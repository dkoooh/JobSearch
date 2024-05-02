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
}
