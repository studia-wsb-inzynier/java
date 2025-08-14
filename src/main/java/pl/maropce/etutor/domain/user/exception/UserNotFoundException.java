package pl.maropce.etutor.domain.user.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND.value(), "User not found. Id: " + id);
    }
}
