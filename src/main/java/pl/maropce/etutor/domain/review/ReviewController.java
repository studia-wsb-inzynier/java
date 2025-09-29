package pl.maropce.etutor.domain.review;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<ReviewDTO>> getReviewsForUser(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ReviewDTO> reviewsForUser = reviewService.getReviewsForUser(userId, pageable);

        return ResponseEntity.ok(reviewsForUser);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<ReviewDTO>> getReviewsByAuthor(
            @PathVariable String authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDTO> reviewsByAuthor = reviewService.getReviewsByAuthor(authorId, pageable);
        return ResponseEntity.ok(reviewsByAuthor);
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
