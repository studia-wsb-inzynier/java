package pl.maropce.etutor.domain.lesson.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class MissingDateRangeParametersException extends BaseException {
    public MissingDateRangeParametersException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
