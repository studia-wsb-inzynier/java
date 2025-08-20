package pl.maropce.etutor.domain.quiz;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.quiz.dto.*;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    public Page<QuizDTO> getAll(Pageable pageable) {
        Page<Quiz> quizPage = quizRepository.findAll(pageable);

        return quizPage.map(quizMapper::toDTO);
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

       List<Question> questionList = new ArrayList<>();
       request.getQuestionList().forEach(question -> {
           Question questionBuild = Question.builder()
                   .question(question.getQuestion())
                   .type(question.getQuestionType())
                   .options(question.getOptions())
                   .correctOptions(question.getCorrectAnswer())
                   .quiz(quiz)
                   .build();
           questionList.add(questionBuild);
       });

       quiz.setQuestionList(questionList);

        Quiz savedQuiz = quizRepository.save(quiz);
        return quizMapper.toDTO(savedQuiz);
    }
}
