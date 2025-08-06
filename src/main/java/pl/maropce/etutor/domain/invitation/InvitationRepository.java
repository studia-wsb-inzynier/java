package pl.maropce.etutor.domain.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationCode, String> {

    Optional<InvitationCode> findByCode(String code);
}
