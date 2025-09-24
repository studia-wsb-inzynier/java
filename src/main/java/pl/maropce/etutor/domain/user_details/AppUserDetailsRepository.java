package pl.maropce.etutor.domain.user_details;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserDetailsRepository extends JpaRepository<AppUserDetails, String> {

    Optional<AppUserDetails> findByUsername(String username);

    boolean existsByUsername(String username);
}

