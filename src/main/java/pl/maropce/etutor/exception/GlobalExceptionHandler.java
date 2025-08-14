package pl.maropce.etutor.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.maropce.etutor.domain.quiz.exception.QuizNotFoundException;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResolver> handleUserNotFoundException(UserNotFoundException ex) {
        ExceptionResolver exceptionResolver = new ExceptionResolver(ex);
        return ResponseEntity.status(ex.getStatusCode()).body(exceptionResolver);
    }

    @ExceptionHandler(QuizNotFoundException.class)
    public ResponseEntity<ExceptionResolver> handleQuizNotFoundException(QuizNotFoundException ex) {
        ExceptionResolver exceptionResolver = new ExceptionResolver(ex);
        return ResponseEntity.status(ex.getStatusCode()).body(exceptionResolver);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResolver> handle(MethodArgumentNotValidException ex) {
        final String message = "Invalid Arguments";

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        BaseException baseException = new BaseException(ex.getStatusCode().value(), message, errors);

        ExceptionResolver resolver = new ExceptionResolver(baseException);
        return ResponseEntity
                .status(baseException.getStatusCode())
                .body(resolver);
    }
}
