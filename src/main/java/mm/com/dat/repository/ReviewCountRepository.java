package mm.com.dat.repository;

import mm.com.dat.entity.ReviewCountInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCountRepository extends JpaRepository<ReviewCountInformation, Long> {
}