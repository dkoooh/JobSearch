package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.WorkExperienceInfoRepository;
import kg.attractor.jobsearch.service.WorkExpInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkExpInfoServiceImpl implements WorkExpInfoService {
    private final WorkExperienceInfoRepository workExpInfoRepository;
    private final ResumeRepository resumeRepository;

    @Override
    public void create(WorkExpInfoCreateDto dto, int resumeId) {
        WorkExperienceInfo info = WorkExperienceInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid resume ID:" + resumeId)))
                .years(dto.getYears())
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .responsibilities(dto.getResponsibilities())
                .build();

        workExpInfoRepository.save(info);
    }

    @Override
    public void update(WorkExpInfoUpdateDto dto, int resumeId) {
        WorkExperienceInfo info = WorkExperienceInfo.builder()
                .id(dto.getId())
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid resume ID:" + resumeId)))
                .years(dto.getYears())
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .responsibilities(dto.getResponsibilities())
                .build();

        workExpInfoRepository.save(info);
    }

    @Override
    public WorkExpInfoDto getById(int id) {
        WorkExperienceInfo info = workExpInfoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid work experience info ID: " + id)
        );

        return WorkExpInfoDto.builder()
                .id(info.getId())
                .years(info.getYears())
                .companyName(info.getCompanyName())
                .position(info.getPosition())
                .responsibilities(info.getResponsibilities())
                .build();
    }

    @Override
    public List<WorkExpInfoDto> getAllByResumeId(int resumeId) {
        List<WorkExperienceInfo> info = workExpInfoRepository.findAllByResumeId(resumeId);

        return info.stream()
                .map(workExperienceInfo -> WorkExpInfoDto.builder()
                        .id(workExperienceInfo.getId())
                        .years(workExperienceInfo.getYears())
                        .companyName(workExperienceInfo.getCompanyName())
                        .position(workExperienceInfo.getPosition())
                        .responsibilities(workExperienceInfo.getResponsibilities())
                        .build())
                .toList();
    }

    @Override
    public void delete(int id) {
//        TODO проверка пользователя

        workExpInfoRepository.deleteById(id);
    }
}
