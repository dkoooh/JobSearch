package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.exception.CustomException;

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
