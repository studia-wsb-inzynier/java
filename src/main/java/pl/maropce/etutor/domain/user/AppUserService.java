package pl.maropce.etutor.domain.user;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.auth.RegisterRequest;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDetailsRepository appUserDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository, AppUserDetailsRepository appUserDetailsRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appUserDetailsRepository = appUserDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUserDetails register(RegisterRequest registerRequest) {

        AppUser appUser = AppUser.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .quizList(List.of())
                .build();

        AppUserDetails appUserDetails = AppUserDetails.builder()
                .appUser(appUser)
                .username(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .build();

        return appUserDetailsRepository.save(appUserDetails);
    }

}
