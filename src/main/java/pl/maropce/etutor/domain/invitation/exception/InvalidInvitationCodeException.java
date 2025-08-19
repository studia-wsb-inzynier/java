package pl.maropce.etutor.domain.invitation.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class InvalidInvitationCodeException extends BaseException {
    public InvalidInvitationCodeException() {
        super(HttpStatus.BAD_REQUEST.value(), "Invalid invitation code");
    }
}
