package pl.maropce.etutor.domain.user_details.auth;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class AuthRequest {
    @Size(min = 3, max = 30)
    private String username;
    private String password;
}
