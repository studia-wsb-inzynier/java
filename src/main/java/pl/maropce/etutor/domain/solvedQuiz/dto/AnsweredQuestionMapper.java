package pl.maropce.etutor.domain.solvedQuiz.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.solvedQuiz.AnsweredQuestion;

@Component
public class AnsweredQuestionMapper {

    public AnsweredQuestionDTO toDTO(AnsweredQuestion answeredQuestion) {
        return AnsweredQuestionDTO.builder()
                .id(answeredQuestion.getId())
                .type(answeredQuestion.getType())
                .correct(answeredQuestion.isCorrect())
                .question(answeredQuestion.getQuestion())
                .allOptions(answeredQuestion.getAllOptions())
                .correctOptions(answeredQuestion.getCorrectOptions())
                .selectedOptions(answeredQuestion.getSelectedOptions())
                .build();
    }

}
