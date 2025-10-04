package pl.maropce.etutor.domain.solvedQuiz;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.quiz.Quiz;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"quiz_id", "solver_id"}) // solver może rozwiązać dany quiz tylko raz
        }
)
public class SolvedQuiz {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @ManyToOne
    @JoinColumn(name = "solver_id")
    private AppUser solver;

    @OneToMany(mappedBy = "solvedQuiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<AnsweredQuestion> answeredQuestions = new ArrayList<>();
}
