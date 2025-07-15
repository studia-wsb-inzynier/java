package pl.maropce.etutor.domain.solvedQuiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.QuestionType;

import java.util.List;

@Getter
@Setter
@Builder
public class AnsweredQuestionDTO {

    private String id;
    private String question;
    private QuestionType type;
    private List<String> allOptions;
    private List<String> selectedOptions;
    private List<String> correctOptions;
}
