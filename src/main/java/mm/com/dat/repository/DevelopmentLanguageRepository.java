package mm.com.dat.repository;

import mm.com.dat.entity.DevelopmentLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevelopmentLanguageRepository extends JpaRepository<DevelopmentLanguage, Long> {
}