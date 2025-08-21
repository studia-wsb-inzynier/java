package pl.maropce.etutor.domain.lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.maropce.etutor.domain.user.AppUser;

import java.time.LocalDateTime;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {


    /**
     * Checks that in provided a time interval exist any lesson
     * assigned to anyone of users (teacher or student).
     * Timing condition:
     * <ul>
     *      <li>:startDateTime < l.endDateTime</li>
     *      <li>:endDateTime > l.beginDateTime</li>
     * </ul>
     *
     * This means that overlapping time intervals are checked.
     * Additionally, a lesson will be found if in the given time interval
     * teacher or student have some lesson.
     *
     * @param startDateTime begin of the time interval of a new lesson
     * @param endDateTime   end of the time interval of a new lesson
     * @param teacher       teacher of a potential lesson
     * @param student       student of a potential lesson
     * @return {@code true}, if exist an overlapping lesson for teacher or student
     */
    @Query("SELECT COUNT(l) > 0 FROM Lesson l WHERE " +
            "(:startDateTime < l.endDateTime AND :endDateTime > l.beginDateTime) " +
            "AND " +
            "(l.teacher = :teacher OR l.student = :teacher " +
            "OR l.teacher = :student OR l.student = :student)"
    )
    boolean existsOverlappingLesson(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime,
                                    @Param("teacher") AppUser teacher,
                                    @Param("student") AppUser student
    );


    /**
     * Checks whether there exists any lesson in the provided time interval
     * that is assigned to either of the users (teacher or student),
     * excluding the lesson with the specified ID.
     * Timing condition:
     * <ul>
     *      <li>:startDateTime &lt; l.endDateTime</li>
     *      <li>:endDateTime &gt; l.beginDateTime</li>
     * </ul>
     *
     * This means that overlapping time intervals are checked.
     * A lesson will be considered overlapping if either the teacher or the student
     * has a lesson in the given time interval, except for the lesson with the given ID.
     *
     * @param startDateTime      the beginning of the time interval of a new lesson
     * @param endDateTime        the end of the time interval of a new lesson
     * @param teacher            the teacher of the potential lesson
     * @param student            the student of the potential lesson
     * @param excludedLessonId   the ID of a lesson to be excluded from the check
     * @return {@code true} if there exists an overlapping lesson for the teacher or student,
     *         excluding the lesson with the given ID
     */
    @Query("SELECT COUNT(l) > 0 FROM Lesson l WHERE " +
            "(:startDateTime < l.endDateTime AND :endDateTime > l.beginDateTime) " +
            "AND " +
            "(l.teacher = :teacher OR l.student = :teacher " +
            "OR l.teacher = :student OR l.student = :student) " +
            "AND " +
            "l.id != :excludedLessonId")
    boolean existsOverlappingLessonExcludingLessonById(@Param("startDateTime") LocalDateTime startDateTime,
                                                       @Param("endDateTime") LocalDateTime endDateTime,
                                                       @Param("teacher") AppUser teacher,
                                                       @Param("student") AppUser student,
                                                       @Param("excludedLessonId") String excludedLessonId);



}
