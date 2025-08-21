package pl.maropce.etutor.domain.lesson.exception;


import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class LessonTimesOverlapException extends BaseException {

    public LessonTimesOverlapException() {
        super(HttpStatus.CONFLICT.value(), "Lesson times overlap with an existing lesson!");
    }
}