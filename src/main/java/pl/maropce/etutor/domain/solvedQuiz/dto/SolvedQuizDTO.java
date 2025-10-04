package pl.maropce.etutor.domain.solvedQuiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SolvedQuizDTO {

    private String id;

    private String title;

    private String quizId;

    private String ownerId;

    private String solverId;

    private List<AnsweredQuestionDTO> answeredQuestions;
}
