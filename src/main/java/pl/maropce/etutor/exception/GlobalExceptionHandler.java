package pl.maropce.etutor.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${date-time.format}")
    private String dateTimeFormat;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResolver> handleBaseException(BaseException ex) {
        ExceptionResolver exceptionResolver = new ExceptionResolver(ex);

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(exceptionResolver);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResolver> handle(MethodArgumentNotValidException ex) {
        final String message = "Invalid Arguments";

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        BaseException baseException = new BaseException(HttpStatus.BAD_REQUEST.value(), message);

        ExceptionResolver resolver = new InvalidArgumentsExceptionResolver(baseException, errors);
        return ResponseEntity
                .status(baseException.getStatusCode())
                .body(resolver);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ExceptionResolver> handleDateTimeParseException(DateTimeParseException ex) {


        BaseException baseException = new BaseException(400, ex.getParsedString() + " is not a valid date. Should match the following pattern: " + dateTimeFormat);

        ExceptionResolver exceptionResolver = new ExceptionResolver(baseException);
        return ResponseEntity.status(400).body(exceptionResolver);
    }
}
