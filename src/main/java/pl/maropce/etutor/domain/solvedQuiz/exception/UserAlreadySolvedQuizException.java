package pl.maropce.etutor.domain.solvedQuiz.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class UserAlreadySolvedQuizException extends BaseException {

    public UserAlreadySolvedQuizException() {
        super(HttpStatus.CONFLICT.value(), "User already solved quiz");
    }
}
