package pl.maropce.etutor.domain.quiz;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.quiz.dto.*;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.solvedQuiz.SolvedQuiz;
import pl.maropce.etutor.domain.solvedQuiz.SolvedQuizRepository;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;
    private final AppUserRepository appUserRepository;
    private final SolvedQuizRepository solvedQuizRepository;

    public Page<QuizDTO> getAll(Pageable pageable) {
        Page<Quiz> quizPage = quizRepository.findAll(pageable);

        return quizPage.map(quizMapper::toDTO);
    }

    public QuizDTO getById(String id) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
        return quizMapper.toDTO(quiz);
    }

    @Transactional
    public void delete(String quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));


        List<SolvedQuiz> solvedQuizzes = solvedQuizRepository.findAllByQuiz(quiz);
        for (SolvedQuiz sq : solvedQuizzes) {
            sq.setQuiz(null);
        }

        List<AppUser> users = appUserRepository.findAllByQuizListContaining(quiz);
        for (AppUser user : users) {
            user.getQuizList().remove(quiz);
        }

        quizRepository.delete(quiz);
    }


    @Transactional
    public QuizDTO create(CreateQuizRequest request, AppUserDetails appUserDetails) {

       Quiz quiz = Quiz.builder()
               .title(request.getTitle())
               .owner(appUserDetails.getAppUser())
               .build();

       List<Question> questionList = new ArrayList<>();
       request.getQuestions().forEach(question -> {
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
