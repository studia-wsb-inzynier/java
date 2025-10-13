package pl.maropce.etutor.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maropce.etutor.domain.quiz.Quiz;

import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {

    List<AppUser> findAllByQuizListContaining(Quiz quiz);
}
