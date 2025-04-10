package pl.maropce.etutor.domain.question.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.QuestionType;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionDTO {

    private String id;
    private String question;

    private QuestionType questionType;

    private List<String> options;
    private List<String> correctOptions;
}
