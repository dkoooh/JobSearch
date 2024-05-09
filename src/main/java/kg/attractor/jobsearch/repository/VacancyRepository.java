package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Integer> {
    List<Vacancy> findAllByAuthorEmail (String email);

    List<Vacancy> findAllByIsActiveTrue(Sort sort);

    List<Vacancy> findAllByCategoryIdAndIsActiveTrue(Integer categoryId, Sort sort);

    List<Vacancy> findAllByIsActiveTrueAndNameContainsIgnoreCase(String name);

    List<Vacancy> findAllByCategoryIdAndIsActiveTrueAndNameContainsIgnoreCase(Integer categoryId, String name, Sort sort);

    @Query("""
                        select v from Vacancy as v where v in (
                            select ra.vacancy from RespondedApplicant as ra where ra.resume.author.id = :applicantId
                        )
            """)
    List<Vacancy> findAllByApplicantId (Integer applicantId);
}
