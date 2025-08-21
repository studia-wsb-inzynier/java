package pl.maropce.etutor.domain.lesson.exception;

import org.springframework.http.HttpStatus;
import pl.maropce.etutor.exception.BaseException;

public class InvalidLessonDates extends BaseException {
        public InvalidLessonDates() {
            super(HttpStatus.BAD_REQUEST.value(), "Invalid dates. Check if your start date is before the end date");
        }
}
