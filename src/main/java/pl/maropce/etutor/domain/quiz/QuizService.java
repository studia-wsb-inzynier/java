package pl.maropce.etutor.domain.quiz;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.question.QuestionRepository;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;
import pl.maropce.etutor.domain.quiz.dto.*;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizMapper quizMapper;

    @Value("${quiz.generator.url}")
    private String quizURL;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizMapper = quizMapper;
    }

    public List<QuizDTO> getAll() {
        List<Quiz> quizList = quizRepository.findAll();

        return quizList.stream()
                .map(quizMapper::toDTO)
                .toList();
    }

    public QuizDTO getById(String id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        return quizMapper.toDTO(quiz);
    }

    public void delete(String id) {
        quizRepository.deleteById(id);
    }

    @Transactional
    public QuizDTO create(CreateQuizRequest request, AppUserDetails appUserDetails) {

       Quiz quiz = Quiz.builder()
               .title(request.getTitle())
               .owner(appUserDetails.getAppUser())
               .build();
       quizRepository.save(quiz);

       List<Question> questionList = new ArrayList<>();
       request.getQuestionList().forEach(question -> {
           Question questionBuild = Question.builder()
                   .question(question.getQuestion())
                   .type(question.getType())
                   .options(question.getOptions())
                   .correctOptions(question.getCorrectOptions())
                   .quiz(quiz)
                   .build();
           questionRepository.save(questionBuild);
           questionList.add(questionBuild);
       });

       quiz.setQuestionList(questionList);

       return quizMapper.toDTO(quiz);
    }

    public List<QuestionDTO> generateQuizWithAI(GenerateQuizRequest request, AppUserDetails appUserDetails) {
        WebClient webClient = WebClient.builder()
                .baseUrl(quizURL)
                .build();

        request.setUserId(appUserDetails.getAppUser().getId());
        System.out.println(request);

        GenerateQuizResponse response = webClient.post()
                .uri("/api/quiz/generate")
                //.bodyValue(request)
                .body(Mono.just(request), GenerateQuizRequest.class)
                .retrieve()
                .bodyToMono(GenerateQuizResponse.class)
                .block();


        return response.getQuestions();
    }
}
