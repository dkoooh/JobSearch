package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoDto;

public interface EduInfoService {
    void create (EduInfoCreateDto dto, int resumeId);

    EduInfoDto getById (int id, String applicantEmail);

    void update (EduInfoUpdateDto dto, int resumeId);

    void delete (Integer id, String applicantEmail);
}
