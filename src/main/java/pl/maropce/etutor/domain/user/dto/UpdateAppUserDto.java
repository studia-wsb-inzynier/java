package pl.maropce.etutor.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAppUserDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}