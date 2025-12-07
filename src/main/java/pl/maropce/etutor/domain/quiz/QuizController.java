package pl.maropce.etutor.domain.quiz;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.quiz.dto.CreateQuizRequest;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public ResponseEntity<Page<QuizDTO>> getQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<QuizDTO> all = quizService.getAll(pageable);
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
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody @Valid CreateQuizRequest createQuizRequest,
                                              @AuthenticationPrincipal AppUserDetails appUserDetails) throws URISyntaxException {
        QuizDTO quizDTO = quizService.create(createQuizRequest, appUserDetails);


        URI location = new URI("/api/lessons/" + quizDTO.getId());

        return ResponseEntity.created(location).body(quizDTO);
    }
}

