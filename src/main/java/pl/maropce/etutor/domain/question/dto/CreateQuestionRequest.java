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

    @Size(min = 1, max = 500)
    @NotBlank
    private String question;

    private QuestionType questionType;

    private List<String> options;

    private List<String> correctAnswer;
}
