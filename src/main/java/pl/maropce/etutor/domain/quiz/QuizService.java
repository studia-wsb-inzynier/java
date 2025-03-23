package pl.maropce.etutor.domain.quiz;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.question.QuestionRepository;
import pl.maropce.etutor.domain.quiz.dto.CreateQuizRequest;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;
import pl.maropce.etutor.domain.quiz.dto.QuizMapper;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizMapper quizMapper;

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
    public QuizDTO create(CreateQuizRequest request) {

       Quiz quiz = Quiz.builder()
               .title(request.getTitle())
               .build();
       quizRepository.save(quiz);

       List<Question> questionList = new ArrayList<>();
       request.getQuestionList().forEach(question -> {
           Question questionBuild = Question.builder()
                   .content(question.getContent())
                   .quiz(quiz)
                   .build();
           questionRepository.save(questionBuild);
           questionList.add(questionBuild);
       });

       quiz.setQuestionList(questionList);
       return quizMapper.toDTO(quiz);
    }
}
