package pl.maropce.etutor.domain.review.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.review.Review;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {

        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .authorId(review.getAuthor().getId())
                .authorName(review.getAuthor().getFirstName())
                .authorLastName(review.getAuthor().getLastName())
                .reviewerId(review.getReviewer().getId())
                .reviewerName(review.getReviewer().getFirstName())
                .reviewerLastName(review.getReviewer().getLastName())
                .build();
    }
}
