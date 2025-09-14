package pl.maropce.etutor.domain.review.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class ReviewAlreadyExistsException extends BaseException {
    public ReviewAlreadyExistsException(String authorId, String reviewerId) {
        super(HttpStatus.CONFLICT.value(), "Review from author " + authorId + " to reviewer " + reviewerId + " already exists");

    }
}
