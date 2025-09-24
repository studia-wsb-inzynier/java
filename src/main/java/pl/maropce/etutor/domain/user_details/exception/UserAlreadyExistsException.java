package pl.maropce.etutor.domain.user_details.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String username) {
        super(HttpStatus.CONFLICT.value(), "Username " + username + " already exists");
    }
}
