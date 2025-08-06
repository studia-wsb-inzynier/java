package pl.maropce.etutor.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.quiz.Quiz;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonIgnore
    @MapsId
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private AppUserDetails appUserDetails;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Quiz> quizList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teacher_student",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<AppUser> students;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    private List<AppUser> teachers;
}
