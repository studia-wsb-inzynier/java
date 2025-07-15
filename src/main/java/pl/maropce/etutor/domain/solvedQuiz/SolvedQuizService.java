package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.quiz.QuizRepository;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SolvedQuizService {

    private final SolvedQuizRepository solvedQuizRepository;
    private final QuizRepository quizRepository;

    public SolvedQuizService(SolvedQuizRepository solvedQuizRepository, QuizRepository quizRepository) {
        this.solvedQuizRepository = solvedQuizRepository;
        this.quizRepository = quizRepository;
    }

    public SolvedQuiz solvedQuiz(String quizId, Map<String, List<String>> answers) {

        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new QuizNotFoundException(quizId));

        List<AnsweredQuestion> answeredQuestions = new ArrayList<>();

        quiz.getQuestionList().forEach(question -> {
            AnsweredQuestion answeredQuestion = AnsweredQuestion.builder()
                    .type(question.getType())
                    .question(question.getQuestion())
                    .allOptions(new ArrayList<>(question.getOptions()))
                    .correctOptions(new ArrayList<>(question.getCorrectOptions()))
                    .selectedOptions(answers.get(question.getId()))
                    .build();

            answeredQuestions.add(answeredQuestion);
        });

        SolvedQuiz solvedQuiz = SolvedQuiz.builder()
                .title(quiz.getTitle())
                .answeredQuestions(answeredQuestions)
                .owner(quiz.getOwner())
                .build();

        return solvedQuizRepository.save(solvedQuiz);
    }
}
