package pl.maropce.etutor.domain.user_details.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.maropce.etutor.domain.email.EmailService;
import pl.maropce.etutor.domain.user.AppUser;
import pl.maropce.etutor.domain.user_details.AppUserDetails;
import pl.maropce.etutor.domain.user_details.AppUserDetailsRepository;
import pl.maropce.etutor.domain.user_details.auth.dto.ChangePasswordRequest;
import pl.maropce.etutor.domain.user_details.auth.dto.RegisterRequest;
import pl.maropce.etutor.domain.user_details.auth.user_activation_token.UserActivationToken;
import pl.maropce.etutor.domain.user_details.auth.user_activation_token.UserActivationTokenRepository;
import pl.maropce.etutor.domain.user_details.exception.InvalidCurrentPasswordException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsRepository appUserDetailsRepository;
    private final UserActivationTokenRepository activationTokenRepository;

    @Transactional
    public AppUserDetails register(RegisterRequest registerRequest) {

        AppUserDetails savedUser = createAppUserDetails(registerRequest);

        UserActivationToken token = createActivationToken(savedUser);

        sendActivationEmail(savedUser.getUsername(), token.getToken());

        return savedUser;
    }

    private AppUserDetails createAppUserDetails(RegisterRequest registerRequest) {
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
                .isEnabled(false)
                .build();

        appUser.setAppUserDetails(appUserDetails);

        return appUserDetailsRepository.save(appUserDetails);
    }

    private UserActivationToken createActivationToken(AppUserDetails appUserDetails) {
        UserActivationToken token = UserActivationToken.builder()
                .appUserDetails(appUserDetails)
                .token(UUID.randomUUID().toString())
                .build();

        return activationTokenRepository.save(token);
    }

    private void sendActivationEmail(String email, String token) {

        String htmlTemplate;
        try {
            htmlTemplate = StreamUtils.copyToString(
                    getClass().getResourceAsStream("/templates/activation-email.html"),
                    StandardCharsets.UTF_8
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String activationLink = buildActivationLink(token);

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
