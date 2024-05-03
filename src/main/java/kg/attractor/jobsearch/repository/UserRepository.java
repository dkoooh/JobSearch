package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findAllByName(String name);

    List<User> findAllByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = """
                select * from USERS where ID in (
                    select APPLICANT_ID from RESUMES
                    where ID in (
                        select RESUME_ID from RESPONDED_APPLICANTS
                        where VACANCY_ID = :vacancyId
                    )
                )
            """) // TODO переделать под jpql
    List<User> findApplicantsByVacancyId(int vacancyId);
}
