package pl.maropce.etutor.domain.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    Page<Review> findByReviewer_Id(String reviewerId, Pageable pageable);
    Page<Review> findByAuthor_Id(String authorId, Pageable pageable);
    boolean existsByAuthor_IdAndReviewer_Id(String authorId, String reviewerId);


}
