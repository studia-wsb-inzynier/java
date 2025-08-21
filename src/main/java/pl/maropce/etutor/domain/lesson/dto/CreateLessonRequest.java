package pl.maropce.etutor.domain.lesson.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateLessonRequest {

    @NotNull
    private String studentId;

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @Future
    @NotNull
    private LocalDateTime beginDateTime;

    @Future
    @NotNull
    private LocalDateTime endDateTime;
}