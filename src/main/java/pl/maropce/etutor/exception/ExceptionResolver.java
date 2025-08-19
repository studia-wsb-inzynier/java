package pl.maropce.etutor.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ExceptionResolver {

    private int statusCode;
    private String message;

    public ExceptionResolver(BaseException exception) {
        statusCode = exception.getStatusCode();
        message = exception.getMessage();

    }
}
