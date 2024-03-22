package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.WorkExperienceInfoDao;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.service.WorkExpInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkExpInfoInfoServiceImpl implements WorkExpInfoService {
    private final WorkExperienceInfoDao workExperienceInfoDao;

    @Override
    public void create(WorkExpInfoCreateDto dto, int resumeId) {
        WorkExperienceInfo info = WorkExperienceInfo.builder()
                .resumeId(resumeId)
                .years(dto.getYears())
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .responsibilities(dto.getResponsibilities())
                .build();

        workExperienceInfoDao.create(info);
    }

    @Override
    public void update(WorkExpInfoUpdateDto dto, int resumeId) {
        WorkExperienceInfo info = WorkExperienceInfo.builder()
                .id(resumeId)
                .resumeId(resumeId)
                .years(dto.getYears())
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .responsibilities(dto.getResponsibilities())
                .build();

        if (info.getId() != null && workExperienceInfoDao.getById(info.getId()).isPresent()) {
            workExperienceInfoDao.update(info);
        } else {
            workExperienceInfoDao.create(info);
        }
    }

    @Override
    public WorkExpInfoDto getById(int id) {
        WorkExperienceInfo info = workExperienceInfoDao.getById(id).orElseThrow(() -> new CustomException("Not found"));

        return WorkExpInfoDto.builder()
                .id(info.getId())
                .resumeId(info.getResumeId())
                .years(info.getYears())
                .companyName(info.getCompanyName())
                .position(info.getPosition())
                .responsibilities(info.getResponsibilities())
                .build();
    }

    @Override
    public void delete(int id) {
        workExperienceInfoDao.delete(id);
    }
}
