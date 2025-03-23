package pl.maropce.etutor.domain.question.dto;


import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.question.Question;

@Component
public class QuestionMapper {

    public QuestionDTO toDTO(Question question) {

        return QuestionDTO.builder()
                .id(question.getId())
                .content(question.getContent())
                .type(question.getType())
                .options(question.getOptions())
                .correctOptions(question.getCorrectOptions())
                .build();
    }
}
