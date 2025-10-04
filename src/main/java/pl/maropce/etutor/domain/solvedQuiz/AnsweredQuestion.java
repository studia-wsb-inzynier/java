package pl.maropce.etutor.domain.solvedQuiz;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.question.QuestionType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnsweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String question;

    private QuestionType type;

    private boolean correct;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> allOptions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> selectedOptions = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> correctOptions = new ArrayList<>();

    @ManyToOne
    private SolvedQuiz solvedQuiz;
}
