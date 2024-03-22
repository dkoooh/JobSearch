package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;

import java.util.List;

public interface WorkExpInfoService {
    void create (WorkExpInfoCreateDto dto, int resumeId);

    void update (WorkExpInfoUpdateDto dto, int resumeId);

    WorkExpInfoDto getById (int id);

    List<WorkExpInfoDto> getByResumeId(int resumeId);

    void delete (int id);
}
