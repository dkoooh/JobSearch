package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;

public interface WorkExpInfoService {
    void create (WorkExpInfoCreateDto dto, int resumeId);

    void update (WorkExpInfoUpdateDto dto, int resumeId);

    WorkExpInfoDto getById (int id);

    void delete (int id);
}
