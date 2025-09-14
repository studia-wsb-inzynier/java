package pl.maropce.etutor.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByReviewer_Id(String reviewerId);
    List<Review> findByAuthor_Id(String authorId);
    boolean existsByAuthor_IdAndReviewer_Id(String authorId, String reviewerId);


}
