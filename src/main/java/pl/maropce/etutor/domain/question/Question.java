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

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ElementCollection
    List<String> options = new ArrayList<>();

    @ElementCollection
    List<String> correctOptions = new ArrayList<>();

    @ManyToOne
    private Quiz quiz;

}
