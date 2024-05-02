package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByResponseId (Integer responseId);
}
