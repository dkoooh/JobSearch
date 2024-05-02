package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<RespondedApplicant, Integer> {
    Optional<RespondedApplicant> findByVacancyIdAndResumeId(Integer vacancyId, Integer resumeId);
}
