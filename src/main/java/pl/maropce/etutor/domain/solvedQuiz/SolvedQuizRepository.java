package pl.maropce.etutor.domain.solvedQuiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, String> {

    boolean existsByQuiz_IdAndSolver_Id(String quizId, String solverId);
}
