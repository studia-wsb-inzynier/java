package pl.maropce.etutor.domain.user_details.auth.user_activation_token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserActivationTokenRepository extends JpaRepository<UserActivationToken, String> {

    Optional<UserActivationToken> findByToken(String token);
}
