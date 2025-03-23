package pl.maropce.etutor.domain.question.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.QuestionType;

import java.util.List;

@Getter
@Setter
public class CreateQuestionRequest {

    @Size(min = 1, max = 250)
    @NotBlank
    private String content;

    private QuestionType type;

    private List<String> options;

    private List<String> correctOptions;
}
