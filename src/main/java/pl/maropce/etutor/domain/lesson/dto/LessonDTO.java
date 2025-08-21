package pl.maropce.etutor.domain.lesson.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LessonDTO {

    private String id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @Future
    @NotNull
    private LocalDateTime startDateTime;

    @Future
    @NotNull
    private LocalDateTime endDateTime;

    @NotNull
    private String teacher;

    @NotNull
    private String student;
}