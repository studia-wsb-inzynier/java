package pl.maropce.etutor.domain.user_details.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class AccountNotActiveException extends BaseException {
    public AccountNotActiveException() {
        super(HttpStatus.UNAUTHORIZED.value(), "Your account is not activated");

    }
}
