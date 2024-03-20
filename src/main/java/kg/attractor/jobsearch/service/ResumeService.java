package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Resume;

import java.security.Signature;
import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumes (String employerEmail) throws CustomException;

    ResumeDto getResumeById(int resumeId);

    List<ResumeDto> getResumesByCategory (int categoryId, String email) throws CustomException;

    List<ResumeDto> getResumesByApplicant (int applicantId);

    void createResume (ResumeDto resume) throws CustomException;

    void updateResume (ResumeDto resume) throws CustomException;

    void deleteResume (int resumeId, String email) throws CustomException;
}
