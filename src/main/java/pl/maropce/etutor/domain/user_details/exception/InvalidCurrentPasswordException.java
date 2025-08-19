package pl.maropce.etutor.domain.user_details.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class InvalidCurrentPasswordException extends BaseException {
    public InvalidCurrentPasswordException() {
        super(HttpStatus.BAD_REQUEST.value(), "Current password is invalid");
    }
}
