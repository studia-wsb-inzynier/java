package pl.maropce.etutor.domain.quiz;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.question.Question;
import pl.maropce.etutor.domain.user.AppUser;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @OneToOne
    private AppUser owner;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Question> questionList = new ArrayList<>();
}
