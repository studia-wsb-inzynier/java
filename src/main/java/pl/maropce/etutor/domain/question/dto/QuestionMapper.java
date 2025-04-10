package pl.maropce.etutor.domain.question.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.question.Question;

@Component
public class QuestionMapper {

    public QuestionDTO toDTO(Question question) {

        return QuestionDTO.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .questionType(question.getType())
                .options(question.getOptions())
                .correctAnswer(question.getCorrectOptions())
                .build();
    }
}
