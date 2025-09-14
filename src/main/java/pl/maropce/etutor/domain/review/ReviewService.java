package pl.maropce.etutor.domain.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.review.dto.CreateReviewRequest;
import pl.maropce.etutor.domain.review.dto.ReviewDTO;
import pl.maropce.etutor.domain.review.exception.ReviewAlreadyExistsException;
import pl.maropce.etutor.domain.review.exception.ReviewNotFoundException;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppUserRepository appUserRepository;
    private final pl.maropce.etutor.domain.review.dto.ReviewMapper reviewMapper;

    public List<ReviewDTO> getReviewsForUser(String userId) {
        return reviewRepository.findByReviewer_Id(userId)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByAuthor(String authorId) {
        return reviewRepository.findByAuthor_Id(authorId)
                .stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }


    public ReviewDTO createForUser(AppUser author, String reviewerId, CreateReviewRequest dto) {

        if (reviewRepository.existsByAuthor_IdAndReviewer_Id(author.getId(), reviewerId)) {
            throw new ReviewAlreadyExistsException(author.getId(), reviewerId);
        }

        AppUser reviewer = appUserRepository.findById(reviewerId)
                .orElseThrow(() -> new UserNotFoundException(reviewerId));

        Review review = Review.builder()
                .rating(dto.getRating())
                .comment(dto.getComment())
                .author(author)
                .reviewer(reviewer)
                .build();

        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    public ReviewDTO getById(String id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDTO)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    public void delete(String id) {
        reviewRepository.deleteById(id);
    }
}
