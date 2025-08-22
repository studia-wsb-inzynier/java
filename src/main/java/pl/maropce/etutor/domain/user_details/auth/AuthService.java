package pl.maropce.etutor.domain.user_details.auth;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.maropce.etutor.domain.email.EmailService;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.exception.InvalidCurrentPasswordException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsRepository appUserDetailsRepository;

    public AuthService(EmailService emailService, PasswordEncoder passwordEncoder, AppUserDetailsRepository appUserDetailsRepository) {
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsRepository = appUserDetailsRepository;
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

        appUser.setAppUserDetails(appUserDetails);


        AppUserDetails savedUser = appUserDetailsRepository.save(appUserDetails);

        sendActivationEmail(appUserDetails.getUsername());

        return savedUser;
    }

    private void sendActivationEmail(String email) {

        String htmlTemplate;
        try {
            htmlTemplate = StreamUtils.copyToString(
                    getClass().getResourceAsStream("/templates/activation-email.html"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String activationLink = buildActivationLink(UUID.randomUUID().toString());

        String htmlContent = htmlTemplate.replace("{{activationLink}}", activationLink);

        emailService.sendHtmlEmail(email, htmlContent, true);
    }

    private String buildActivationLink(String token) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/auth/activate")
                .queryParam("token", token)
                .toUriString();
    }

    public void changePassword(String username, ChangePasswordRequest request) {
        AppUserDetails user = appUserDetailsRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidCurrentPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        appUserDetailsRepository.save(user);
    }
}
