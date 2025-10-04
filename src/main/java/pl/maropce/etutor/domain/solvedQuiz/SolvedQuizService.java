package pl.maropce.etutor.domain.solvedQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolvedQuizDTO;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolvedQuizMapper;
import pl.maropce.etutor.domain.solvedQuiz.exception.UserAlreadySolvedQuizException;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SolvedQuizService {

    private final SolvedQuizRepository solvedQuizRepository;
    private final QuizRepository quizRepository;
    private final AppUserRepository appUserRepository;
    private final SolvedQuizMapper solvedQuizMapper;

    public SolvedQuizDTO solveQuiz(String quizId, Map<String, List<String>> answers, String solverId) {

        if(solvedQuizRepository.existsByQuiz_IdAndSolver_Id(quizId, solverId)) {
            throw new UserAlreadySolvedQuizException();
        }

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));

        AppUser solver = appUserRepository.findById(solverId).orElseThrow(() -> new UserNotFoundException(solverId));

        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();

        quiz.getQuestionList().forEach(question -> {
            AnsweredQuestion answeredQuestion = AnsweredQuestion.builder()
                    .type(question.getType())
                    .question(question.getQuestion())
                    .allOptions(new ArrayList<>(question.getOptions()))
                    .correctOptions(new ArrayList<>(question.getCorrectOptions()))
                    .selectedOptions(answers.get(question.getId()))
                    .build();

            answeredQuestion.setCorrect(isAnswerCorrect(answeredQuestion));

            answeredQuestions.add(answeredQuestion);
        });

        SolvedQuiz solvedQuiz = SolvedQuiz.builder()
                .title(quiz.getTitle())
                .quiz(quiz)
                .answeredQuestions(answeredQuestions)
                .owner(quiz.getOwner())
                .solver(solver)
                .build();

        SolvedQuiz save = solvedQuizRepository.save(solvedQuiz);
        return solvedQuizMapper.toDTO(save);
    }

    private boolean isAnswerCorrect(AnsweredQuestion answeredQuestion) {
       return switch (answeredQuestion.getType()) {
           case SINGLE, TRUE_FALSE, MULTI, FILL -> new HashSet<>(answeredQuestion.getCorrectOptions())
                   .equals(new HashSet<>(answeredQuestion.getSelectedOptions()));

           case ORDER -> answeredQuestion.getCorrectOptions()
                   .equals(answeredQuestion.getSelectedOptions());

       };
    }
}
