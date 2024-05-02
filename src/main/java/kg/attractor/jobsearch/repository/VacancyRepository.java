package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findAllByIsActive(Boolean active);

    List<Vacancy> findAllByAuthorEmail (String email);

    List<Vacancy> findAllByCategoryId (Integer categoryId);

    @Query("""
                        select v from Vacancy as v where v in (
                            select ra.vacancy from RespondedApplicant as ra where ra.resume.author.id = :applicantId
                        )
            """)
    List<Vacancy> findAllByApplicantId (Integer applicantId);
}
