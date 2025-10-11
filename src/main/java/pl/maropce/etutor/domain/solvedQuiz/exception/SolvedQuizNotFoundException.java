package pl.maropce.etutor.domain.solvedQuiz.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class SolvedQuizNotFoundException extends BaseException {
    public SolvedQuizNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND.value(), "Solved Quiz with id " + id + " not found");
    }
}
