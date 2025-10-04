package pl.maropce.etutor.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {

    private String id;

    private int rating;

    private String comment;

    private String authorId;
    private String authorName;
    private String authorLastName;

    private String reviewerId;
    private String reviewerName;
    private String reviewerLastName;

}
