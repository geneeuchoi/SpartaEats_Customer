package like.heocholi.spartaeats.repository;

import like.heocholi.spartaeats.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long>, ReviewRepositoryCustom{

    Optional<Review> findByStoreIdAndId(Long reviewId, Long storeId);
}
