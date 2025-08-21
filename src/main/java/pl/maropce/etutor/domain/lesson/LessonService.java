package pl.maropce.etutor.domain.lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.lesson.dto.CreateLessonRequest;
import pl.maropce.etutor.domain.lesson.dto.LessonDTO;
import pl.maropce.etutor.domain.lesson.dto.LessonMapper;
import pl.maropce.etutor.domain.lesson.dto.UpdateLessonRequest;
import pl.maropce.etutor.domain.lesson.exception.InvalidLessonDates;
import pl.maropce.etutor.domain.lesson.exception.LessonNotFoundException;
import pl.maropce.etutor.domain.lesson.exception.LessonTimesOverlapException;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user.AppUserRepository;
import pl.maropce.etutor.domain.user.exception.UserNotFoundException;

import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final AppUserRepository appUserRepository;
    private final LessonMapper lessonMapper;

    public LessonService(LessonRepository lessonRepository, AppUserRepository appUserRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.appUserRepository = appUserRepository;
        this.lessonMapper = lessonMapper;
    }

    public LessonDTO createLesson(CreateLessonRequest request, String teacherId) {

        if (request.getBeginDateTime().isAfter(request.getEndDateTime())) {
            throw new InvalidLessonDates();
        }

        AppUser student = appUserRepository.findById(request.getStudentId())
                .orElseThrow(() -> new UserNotFoundException(request.getStudentId()));

        AppUser teacher = appUserRepository.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(teacherId));

        if (lessonRepository.existsOverlappingLesson(request.getBeginDateTime(), request.getEndDateTime(), teacher, student)) {
            throw new LessonTimesOverlapException();
        }

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .beginDateTime(request.getBeginDateTime())
                .endDateTime(request.getEndDateTime())
                .student(student)
                .teacher(teacher)
                .build();

        Lesson save = lessonRepository.save(lesson);

        return lessonMapper.toDTO(save);
    }

    public LessonDTO updateLesson(String lessonId, UpdateLessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        if (lessonRepository.existsOverlappingLessonExcludingLessonById(
                lessonRequest.getBeginDateTime(),
                lessonRequest.getEndDateTime(),
                lesson.getTeacher(),
                lesson.getStudent(),
                lesson.getId())) {
            throw new LessonTimesOverlapException();
        }

        if (lessonRequest.getBeginDateTime().isAfter(lessonRequest.getEndDateTime())) {
            throw new InvalidLessonDates();
        }

        lesson.setTitle(lessonRequest.getTitle());
        lesson.setBeginDateTime(lessonRequest.getBeginDateTime());
        lesson.setEndDateTime(lessonRequest.getEndDateTime());

        Lesson updatetedLesson = lessonRepository.save(lesson);

        return lessonMapper.toDTO(updatetedLesson);
    }

    public Page<LessonDTO> getAllLessonsByUserId(AppUser appUser, Pageable pageable) {

        Page<Lesson> allLessons = lessonRepository.findAllByUser(appUser, pageable);

        return allLessons.map(lessonMapper::toDTO);
    }

    public void deleteLesson(String lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    public LessonDTO getLessonById(String lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException(lessonId));

        return lessonMapper.toDTO(lesson);
    }
}
