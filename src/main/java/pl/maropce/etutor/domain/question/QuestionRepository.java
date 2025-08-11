package pl.maropce.etutor.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated
@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
}
