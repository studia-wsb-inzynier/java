package pl.maropce.etutor.domain.quiz;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.quiz.dto.CreateQuizRequest;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getQuizzes() {
        List<QuizDTO> all = quizService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable String id) {
        return ResponseEntity.ok(quizService.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        quizService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest) {
        QuizDTO quizDTO = quizService.create(createQuizRequest);
        return ResponseEntity.ok(quizDTO);
    }
}

