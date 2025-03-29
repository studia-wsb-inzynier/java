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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private AppUserDetails appUserDetails;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Quiz> quizList;
}
