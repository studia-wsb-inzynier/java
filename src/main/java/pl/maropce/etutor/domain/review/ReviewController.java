package pl.maropce.etutor.domain.review;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.review.dto.CreateReviewRequest;
import pl.maropce.etutor.domain.review.dto.ReviewDTO;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviewer/{userId}")
    public List<ReviewDTO> getReviewsForUser(@PathVariable String userId) {
        return reviewService.getReviewsForUser(userId);
    }

    @GetMapping("/author/{authorId}")
    public List<ReviewDTO> getReviewsByAuthor(@PathVariable String authorId) {
        return reviewService.getReviewsByAuthor(authorId);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<ReviewDTO> createForUser(
            @PathVariable String userId,
            @Valid @RequestBody CreateReviewRequest dto,
            @AuthenticationPrincipal AppUserDetails author
    ) {
        return ResponseEntity.ok(reviewService.createForUser(author.getAppUser(), userId, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
