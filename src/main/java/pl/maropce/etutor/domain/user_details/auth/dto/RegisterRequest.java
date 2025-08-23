package pl.maropce.etutor.domain.user_details.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.user_details.Role;

@Getter
@Setter
public class RegisterRequest {
    @Email
    @Size(min = 3, max = 30)
    private String email;

    @NotBlank
    @Size(min = 3, max = 30)
    private String password;

    @NotBlank
    @Size(min = 3, max = 30)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 30)
    private String lastName;


    private String phoneNumber;
    private Role role;
}
