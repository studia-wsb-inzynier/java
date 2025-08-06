package pl.maropce.etutor.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.maropce.etutor.domain.user_details.Role;

@Getter
@Setter
@Builder
public class AppUserDTO {

    private String id;
    private Role role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
