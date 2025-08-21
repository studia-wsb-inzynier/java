package pl.maropce.etutor.domain.lesson.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class LessonNotFoundException extends BaseException {

    public LessonNotFoundException(String id) {
        super(HttpStatus.NOT_FOUND.value(), "Lesson with id " + id + " not found");
    }
}
