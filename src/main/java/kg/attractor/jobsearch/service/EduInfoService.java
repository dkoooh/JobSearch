package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoDto;

import java.util.List;

public interface EduInfoService {
    void create (EduInfoCreateDto dto, int resumeId);

    EduInfoDto getById (int id);

    List<EduInfoDto> getAllByResumeId(int resumeId);

    void update (EduInfoUpdateDto dto, int resumeId);

    EduInfoUpdateDto convertToUpdateDto(EduInfoDto dto);
}
