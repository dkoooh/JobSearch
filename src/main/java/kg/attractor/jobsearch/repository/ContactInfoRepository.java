package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Integer> {
    List<ContactInfo> findAllByResumeId(int resumeId);

    Optional<ContactInfo> findByResumeIdAndTypeId (int resumeId, int typeId);
}
