package pl.maropce.etutor.domain.question;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 1000)
    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType type;


    @ElementCollection(fetch = FetchType.EAGER)
    List<String> options = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    List<String> correctOptions = new ArrayList<>();

    @ManyToOne
    private Quiz quiz;

}
