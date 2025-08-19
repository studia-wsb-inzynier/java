package pl.maropce.etutor.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class InvalidArgumentsExceptionResolver extends ExceptionResolver {

    private Map<String, String> errors;

    public InvalidArgumentsExceptionResolver(BaseException baseException, Map<String, String> errors) {
        super(baseException);
        this.errors = errors;
    }
}