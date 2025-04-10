package pl.maropce.etutor.domain.quiz.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.question.dto.QuestionDTO;
import pl.maropce.etutor.domain.question.dto.QuestionMapper;
import pl.maropce.etutor.domain.quiz.Quiz;

import java.util.List;

@Component
public class QuizMapper {

    private final QuestionMapper questionMapper;

    public QuizMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    public QuizDTO toDTO(Quiz quiz) {
        List<QuestionDTO> questionList = quiz.getQuestionList().stream()
                .map(questionMapper::toDTO)
                .toList();

        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .ownerId(quiz.getOwner() != null ? quiz.getOwner().getId() : null)
                .questions(questionList)
                .build();
    }
}
