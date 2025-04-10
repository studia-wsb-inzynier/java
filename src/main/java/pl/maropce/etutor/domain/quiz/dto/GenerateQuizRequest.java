package pl.maropce.etutor.domain.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.maropce.etutor.domain.question.QuestionType;

@Getter
@Setter
@Builder
@ToString
public class GenerateQuizRequest {

    private String userId;

    private int questionCount;
    private int difficulty;
    private QuestionType[] questionType;
    private String content;
}
