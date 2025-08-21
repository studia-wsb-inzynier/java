package pl.maropce.etutor.domain.lesson;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.maropce.etutor.domain.lesson.dto.CreateLessonRequest;
import pl.maropce.etutor.domain.lesson.dto.LessonDTO;
import pl.maropce.etutor.domain.lesson.dto.UpdateLessonRequest;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    @PostMapping
    public ResponseEntity<LessonDTO> createLesson(
            @RequestBody @Valid CreateLessonRequest createLessonRequest,
            @AuthenticationPrincipal AppUserDetails appUserDetails
            ) throws URISyntaxException {
        LessonDTO lesson = lessonService.createLesson(createLessonRequest, appUserDetails.getId());

        URI resourceURI = new URI("/api/lessons/" + lesson.getId());

        return ResponseEntity.created(resourceURI).body(lesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(
            @PathVariable String id,
            @RequestBody @Valid UpdateLessonRequest lessonRequest
    ) {
        LessonDTO lessonDTO = lessonService.updateLesson(id, lessonRequest);

        return ResponseEntity.ok(lessonDTO);
    }
}
