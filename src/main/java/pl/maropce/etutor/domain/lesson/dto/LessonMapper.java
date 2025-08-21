package pl.maropce.etutor.domain.lesson.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.lesson.Lesson;

@Component
public class LessonMapper {

    public LessonDTO toDTO(Lesson lesson) {

        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .startDateTime(lesson.getBeginDateTime())
                .endDateTime(lesson.getEndDateTime())
                .teacher(lesson.getTeacher().getId())
                .student(lesson.getStudent().getId())
                .build();
    }
}