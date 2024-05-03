package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getAll(String employerEmail);

    Page<ResumeDto> getAllActive(int page);

    ResumeDto getById(int resumeId);

    List<ResumeDto> getAllByCategory(int categoryId, String email);

    List<ResumeDto> getAllByApplicant(int applicantId);

    void create (ResumeCreateDto resume, Authentication auth);

    void update (ResumeUpdateDto resume, Authentication auth);

    void delete(int resumeId, String email);
}
