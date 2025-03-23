package pl.maropce.etutor.domain.quiz.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class QuizDTO {

    private String id;
    private String title;
    private List<QuestionDTO> questionList;
}
