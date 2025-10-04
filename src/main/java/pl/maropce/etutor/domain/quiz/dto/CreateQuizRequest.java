package pl.maropce.etutor.domain.quiz.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.dto.CreateQuestionRequest;

import java.util.List;

@Getter
@Setter
public class CreateQuizRequest {
    
    @Size(min = 1, max = 30)
    @NotBlank
    private String title;

    @Valid
    List<CreateQuestionRequest> questions;
}
