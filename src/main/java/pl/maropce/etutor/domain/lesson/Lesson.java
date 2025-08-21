package pl.maropce.etutor.domain.lesson;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.user.AppUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne
    private AppUser teacher;

    @ManyToOne
    private AppUser student;
}
