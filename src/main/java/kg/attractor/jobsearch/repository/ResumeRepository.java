package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    List<Resume> findByCategory (Category category);

    List<Resume> findByIsActive (Boolean isActive);
}
