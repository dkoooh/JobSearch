package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Integer> {
    List<EducationInfo> findAllByResumeId(int resumeId);
}
