package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority getReferenceByAuthorityName (String name);
}
