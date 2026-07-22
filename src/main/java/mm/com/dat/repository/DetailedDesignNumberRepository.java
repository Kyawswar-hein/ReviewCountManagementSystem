package mm.com.dat.repository;

import mm.com.dat.entity.DetailedDesignNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailedDesignNumberRepository extends JpaRepository<DetailedDesignNumber, Long> {
}