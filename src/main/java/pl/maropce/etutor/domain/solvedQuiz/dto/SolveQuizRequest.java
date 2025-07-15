package pl.maropce.etutor.domain.solvedQuiz.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SolveQuizRequest {
    private String quizId;
    private Map<String, List<String>> answers;
}
