package pl.maropce.etutor.domain.solvedQuiz.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.solvedQuiz.SolvedQuiz;

import java.util.stream.Collectors;

@Component
public class SolvedQuizMapper {

    private final AnsweredQuestionMapper answeredQuestionMapper;

    public SolvedQuizMapper(AnsweredQuestionMapper answeredQuestionMapper) {
        this.answeredQuestionMapper = answeredQuestionMapper;
    }

    public SolvedQuizDTO toDTO(SolvedQuiz solvedQuiz) {
        return SolvedQuizDTO.builder()
                .id(solvedQuiz.getId())
                .title(solvedQuiz.getTitle())
                .quizId(solvedQuiz.getQuiz().getId())
                .ownerId(solvedQuiz.getOwner().getId())
                .solverId(solvedQuiz.getSolver().getId())
                .answeredQuestions(solvedQuiz.getAnsweredQuestions()
                        .stream()
                        .map(answeredQuestionMapper::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

}
