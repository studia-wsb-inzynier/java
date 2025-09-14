package pl.maropce.etutor.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Setter
@Getter
public class CreateReviewRequest {

    @Min(1)
    @Max(10)
    private int rating;

    @Length(max = 200)
    private String comment;
}
