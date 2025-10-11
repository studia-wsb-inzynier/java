package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.maropce.etutor.domain.user.AppUser;

import java.util.List;

public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, String> {

    boolean existsByQuiz_IdAndSolver_Id(String quizId, String solverId);

    Page<SolvedQuiz> findAllBySolver(AppUser solver, Pageable pageable);

    Page<SolvedQuiz> findAllBySolver_IdAndOwner_Id(String solverId, String authorId, Pageable pageable);
}
