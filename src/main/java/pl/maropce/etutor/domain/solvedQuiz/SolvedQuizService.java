package pl.maropce.etutor.domain.solvedQuiz;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolvedQuizDTO;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolvedQuizMapper;
import pl.maropce.etutor.domain.solvedQuiz.exception.SolvedQuizNotFoundException;
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


    public Page<SolvedQuizDTO> getAllBySolver(AppUser appUser, Pageable pageable) {

        Page<SolvedQuiz> allBySolver = solvedQuizRepository.findAllBySolver(appUser, pageable);

        return allBySolver.map(solvedQuizMapper::toDTO);
    }

    public SolvedQuizDTO solveQuiz(String quizId, Map<String, List<String>> answers, String solverId) {

        if(solvedQuizRepository.existsByQuiz_IdAndSolver_Id(quizId, solverId)) {
            throw new UserAlreadySolvedQuizException();
        }

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));

        AppUser solver = appUserRepository.findById(solverId).orElseThrow(() -> new UserNotFoundException(solverId));

        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();

        SolvedQuiz solvedQuiz = SolvedQuiz.builder()
                .title(quiz.getTitle())
                .quiz(quiz)
                .answeredQuestions(answeredQuestions)
                .owner(quiz.getOwner())
                .solver(solver)
                .build();

        quiz.getQuestionList().forEach(question -> {
            AnsweredQuestion answeredQuestion = AnsweredQuestion.builder()
                    .type(question.getType())
                    .question(question.getQuestion())
                    .allOptions(new ArrayList<>(question.getOptions()))
                    .correctOptions(new ArrayList<>(question.getCorrectOptions()))
                    .selectedOptions(answers.get(question.getId()))
                    .solvedQuiz(solvedQuiz)
                    .build();

            answeredQuestion.setCorrect(isAnswerCorrect(answeredQuestion));

            answeredQuestions.add(answeredQuestion);
        });

        SolvedQuiz saved = solvedQuizRepository.save(solvedQuiz);
        return solvedQuizMapper.toDTO(saved);
    }

    private boolean isAnswerCorrect(AnsweredQuestion answeredQuestion) {
       return switch (answeredQuestion.getType()) {
           case SINGLE, TRUE_FALSE, MULTI, FILL -> new HashSet<>(answeredQuestion.getCorrectOptions())
                   .equals(new HashSet<>(answeredQuestion.getSelectedOptions()));

           case ORDER -> answeredQuestion.getCorrectOptions()
                   .equals(answeredQuestion.getSelectedOptions());

       };
    }

    public SolvedQuizDTO getById(String id) {
        SolvedQuiz solvedQuiz = solvedQuizRepository.findById(id).orElseThrow(() -> new SolvedQuizNotFoundException(id));
        return solvedQuizMapper.toDTO(solvedQuiz);
    }

    public Page<SolvedQuizDTO> getAllByUserIdAndAuthor(String userId, String authorId, Pageable pageable) {
        Page<SolvedQuiz> allBySolverIdAndOwnerId = solvedQuizRepository.findAllBySolver_IdAndOwner_Id(userId, authorId, pageable);
        return allBySolverIdAndOwnerId.map(solvedQuizMapper::toDTO);
    }
}
