package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolveQuizRequest;

@RestController
@RequestMapping("/api/solved-quiz")
public class SolvedQuizController {


    private final SolvedQuizService solvedQuizService;

    public SolvedQuizController(SolvedQuizService solvedQuizService) {
        this.solvedQuizService = solvedQuizService;
    }

    @PostMapping
    public ResponseEntity<SolvedQuiz> solveQuiz(@RequestBody SolveQuizRequest request) {
        SolvedQuiz solvedQuiz = solvedQuizService.solvedQuiz(request.getQuizId(), request.getAnswers());

        return ResponseEntity.ok(solvedQuiz);
    }
}
