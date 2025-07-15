package pl.maropce.etutor.domain.solvedQuiz.dto;

import pl.maropce.etutor.domain.solvedQuiz.AnsweredQuestion;

public class AnsweredQuestionMapper {

    public AnsweredQuestionDTO toDTO(AnsweredQuestion answeredQuestion) {
        return AnsweredQuestionDTO.builder()
                .id(answeredQuestion.getId())
                .type(answeredQuestion.getType())
                .question(answeredQuestion.getQuestion())
                .allOptions(answeredQuestion.getAllOptions())
                .correctOptions(answeredQuestion.getCorrectOptions())
                .selectedOptions(answeredQuestion.getSelectedOptions())
                .build();
    }

}
