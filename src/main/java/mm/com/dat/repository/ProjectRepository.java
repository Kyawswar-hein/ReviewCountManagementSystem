package mm.com.dat.repository;

import mm.com.dat.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // This interface automatically provides methods like:
    // .save(entity)
    // .findAll()
    // .findById(id)
    // .delete(entity)
}