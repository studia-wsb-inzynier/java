package pl.maropce.etutor.domain.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.quiz.dto.QuizDTO;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private final AppUserService appUserService;

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PatchMapping("/{id}/quizzes/{quizId}")
    public ResponseEntity<Void> addQuizToUser(@PathVariable String id, @PathVariable String quizId) {
        appUserService.addQuizToUser(id, quizId);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<QuizDTO>> getUserQuizzes(@PathVariable String id) {
        List<QuizDTO> userQuizzes = appUserService.getUserQuizzes(id);

        return ResponseEntity.ok(userQuizzes);
    }
}
