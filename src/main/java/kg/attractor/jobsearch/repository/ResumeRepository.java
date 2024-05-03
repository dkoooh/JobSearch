package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    List<Resume> findAllByCategoryId(Integer categoryId);

    List<Resume> findAllByIsActiveTrue();

    List<Resume> findAllByAuthorId (Integer authorId);
}
