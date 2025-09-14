package pl.maropce.etutor.domain.review.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class ReviewNotFoundException extends BaseException {
    public ReviewNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND.value(), "Review with id " + id + " not found");
    }
}
