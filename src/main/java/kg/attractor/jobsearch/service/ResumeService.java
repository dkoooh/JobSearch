package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;

import java.security.Signature;
import java.util.List;

public interface ResumeService {
    Resume getResumeById(int resumeId);

    List<Resume> getResumesByCategory (int categoryId);

    List<Resume> getResumesByApplicant (int applicantId);

    void createResume (ResumeDto resume);

    void updateResume (ResumeDto resume);

    void deleteResume (int resumeId, String email);
}
