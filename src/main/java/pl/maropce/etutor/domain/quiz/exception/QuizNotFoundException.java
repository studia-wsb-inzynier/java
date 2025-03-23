package pl.maropce.etutor.domain.quiz.exception;


import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class QuizNotFoundException extends BaseException {

    public QuizNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND.value(), "Quiz with id " + id + " not found");
    }
}
