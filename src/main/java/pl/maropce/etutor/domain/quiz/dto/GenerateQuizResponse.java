package pl.maropce.etutor.domain.quiz.dto;

import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;

import java.util.List;

@Getter
@Setter
public class GenerateQuizResponse {
    private List<QuestionDTO> questions;
}
