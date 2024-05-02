package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceInfoRepository extends JpaRepository<WorkExperienceInfo, Integer> {
    List<WorkExperienceInfo> findAllByResumeId (int resumeId);
}
