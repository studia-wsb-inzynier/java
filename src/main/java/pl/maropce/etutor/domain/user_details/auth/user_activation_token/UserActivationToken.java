package pl.maropce.etutor.domain.user_details.auth.user_activation_token;

import jakarta.persistence.*;
import lombok.*;
import pl.maropce.etutor.domain.user_details.AppUserDetails;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserActivationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    @OneToOne
    private AppUserDetails appUserDetails;
}
