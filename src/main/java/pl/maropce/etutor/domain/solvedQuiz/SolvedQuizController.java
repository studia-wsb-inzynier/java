package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        System.out.println(appUserDetails);
        SolvedQuizDTO solvedQuiz = solvedQuizService.solveQuiz(request.getQuizId(), request.getAnswers(), appUserDetails.getAppUser().getId());

        return ResponseEntity.ok(solvedQuiz);
    }
}
