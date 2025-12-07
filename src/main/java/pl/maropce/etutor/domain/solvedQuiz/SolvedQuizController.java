package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolveQuizRequest;
import pl.maropce.etutor.domain.solvedQuiz.dto.SolvedQuizDTO;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

@RestController
@RequestMapping("/api/solved-quizzes")
public class SolvedQuizController {


    private final SolvedQuizService solvedQuizService;

    public SolvedQuizController(SolvedQuizService solvedQuizService) {
        this.solvedQuizService = solvedQuizService;
    }

    @PostMapping
    public ResponseEntity<SolvedQuizDTO> solveQuiz(
            @RequestBody SolveQuizRequest request,
            @AuthenticationPrincipal AppUserDetails appUserDetails
    ) {
        SolvedQuizDTO solvedQuiz = solvedQuizService.solveQuiz(request.getQuizId(), request.getAnswers(), appUserDetails.getAppUser().getId());

        return ResponseEntity.ok(solvedQuiz);
    }

    @GetMapping
    public ResponseEntity<Page<SolvedQuizDTO>> getAllMySolvedQuizzes(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<SolvedQuizDTO> all = solvedQuizService.getAllBySolver(appUserDetails.getAppUser(), pageable);
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolvedQuizDTO> getQuizById(@PathVariable String id) {
        SolvedQuizDTO solvedQuizDTO = solvedQuizService.getById(id);

        return ResponseEntity.ok(solvedQuizDTO);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<SolvedQuizDTO>> getAllSolvedQuizzesByUserId(
            @PathVariable String userId,
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<SolvedQuizDTO> allByUserIdAndAuthor = solvedQuizService.getAllByUserIdAndAuthor(userId, appUserDetails.getAppUser().getId(), pageable);

        return ResponseEntity.ok(allByUserIdAndAuthor);

    }

}
