package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumes (String employerEmail);

    ResumeDto getResumeById(int resumeId);

    List<ResumeDto> getResumesByCategory (int categoryId, String email);

    List<ResumeDto> getResumesByApplicant (int applicantId);

    void create (ResumeCreateDto resume, Authentication auth);

    void update (ResumeUpdateDto resume, Authentication auth);

    void deleteResume (int resumeId, String email);
}
