package pl.maropce.etutor.exception;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseException extends RuntimeException {
    private final int statusCode;
    private final String message;
    private Map<String, String> errors;



}
