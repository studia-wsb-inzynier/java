package pl.maropce.etutor.domain.lesson.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class UpdateLessonRequest {

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