package pl.maropce.etutor.domain.invitation;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.user.AppUser;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvitationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String code;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private AppUser teacher;

    private LocalDateTime createdAt;

}
