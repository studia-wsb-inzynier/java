package pl.maropce.etutor.domain.user.dto;

import org.springframework.stereotype.Component;
import pl.maropce.etutor.domain.user.AppUser;

@Component
public class AppUserMapper {

    public AppUserDTO toDTO(AppUser appUser) {

        return AppUserDTO.builder()
                .id(appUser.getId())
                .role(appUser.getAppUserDetails().getRole())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .phoneNumber(appUser.getPhoneNumber())
                .build();
    }
}
