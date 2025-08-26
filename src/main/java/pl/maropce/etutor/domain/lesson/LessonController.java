package pl.maropce.etutor.domain.lesson;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping
    public ResponseEntity<Page<LessonDTO>> getAllLessons(
            @AuthenticationPrincipal AppUserDetails appUserDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ALL") String rangeType,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "beginDateTime"));

        Page<LessonDTO> allLessonsByUserId = lessonService.getAllLessonsByUserId(
                appUserDetails.getAppUser(),
                pageable,
                rangeType,
                year,
                month,
                day);

        return ResponseEntity.ok(allLessonsByUserId);
    }

    @GetMapping("{lessonId}")
    public ResponseEntity<LessonDTO> getLesson(@PathVariable String lessonId) {
        LessonDTO lessonDTO = lessonService.getLessonById(lessonId);

        return ResponseEntity.ok(lessonDTO);
    }

    @DeleteMapping("{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable String lessonId) {

        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}
