package mm.com.dat.repository;

import mm.com.dat.entity.DetailedDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedDesignRepository extends JpaRepository<DetailedDesign, Long> {
}
