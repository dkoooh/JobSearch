package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ResumeService {
    Resume getResumeById(int resumeId);

    List<Resume> getResumesByCategory (int categoryId);

    List<Resume> getResumesByApplicant (int applicantId);

    void createResume (Resume resume);

    void updateResume (Resume resume);

    void deleteResume (int resumeId);
}
