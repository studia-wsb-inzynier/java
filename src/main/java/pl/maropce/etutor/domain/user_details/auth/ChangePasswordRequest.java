package pl.maropce.etutor.domain.user_details.auth;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {

    private String oldPassword;

    @Size(min = 3, max = 30)
    private String newPassword;
}
