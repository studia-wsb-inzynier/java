package pl.maropce.etutor.domain.user_details.auth.user_activation_token;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserActivationTokenService {

    private final UserActivationTokenRepository activationTokenRepository;

    public UserActivationTokenService(UserActivationTokenRepository activationTokenRepository) {
        this.activationTokenRepository = activationTokenRepository;
    }

    @Transactional
    public void activateUser(String token) {
        activationTokenRepository.findByToken(token).ifPresent(activationToken -> {
            activationToken.getAppUserDetails().setEnabled(true);
            activationTokenRepository.delete(activationToken);
        });
    }
}
