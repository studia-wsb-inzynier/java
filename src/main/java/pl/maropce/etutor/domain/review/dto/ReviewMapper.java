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
                .author(review.getAuthor().getId())
                .reviewer(review.getReviewer().getId())
                .build();
    }
}
