package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoDto;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.repository.EducationInfoRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.EduInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EduInfoServiceImpl implements EduInfoService {
    private final EducationInfoRepository eduInfoRepository;
    private final ResumeRepository resumeRepository;
    private final EducationInfoRepository educationInfoRepository;

    public void create(EduInfoCreateDto dto, int resumeId) {
        EducationInfo eduInfo = EducationInfo.builder()
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid resume")))
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .degree(dto.getDegree())
                .build();

        eduInfoRepository.save(eduInfo);
    }

    @Override
    public void update(EduInfoUpdateDto dto, int resumeId) {
        EducationInfo eduInfo = EducationInfo.builder()
                .id(dto.getId())
                .resume(resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid resume")))
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .degree(dto.getDegree())
                .build();

        eduInfoRepository.save(eduInfo);
    }

    @Override
    public EduInfoDto getById(int id) {
        EducationInfo eduInfo = educationInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Education info not found"));

        return EduInfoDto.builder()
                .id(eduInfo.getId())
                .institution(eduInfo.getInstitution())
                .program(eduInfo.getProgram())
                .startDate(eduInfo.getStartDate())
                .endDate(eduInfo.getEndDate())
                .degree(eduInfo.getDegree())
                .build();
    }

    @Override
    public List<EduInfoDto> getAllByResumeId(int resumeId) {
        List<EducationInfo> eduInfo = educationInfoRepository.findAllByResumeId(resumeId);

        return eduInfo.stream()
                .map(info -> EduInfoDto.builder()
                        .id(info.getId())
                        .institution(info.getInstitution())
                        .program(info.getProgram())
                        .startDate(info.getStartDate())
                        .endDate(info.getEndDate())
                        .degree(info.getDegree())
                        .build())
                .toList();
    }


    @Override
    public void delete(Integer id, String applicantEmail) {
//        TODO валидация пользователя

        educationInfoRepository.deleteById(id);
    }

    @Override
    public EduInfoUpdateDto convertToUpdateDto(EduInfoDto dto) {
        return EduInfoUpdateDto.builder()
                .id(dto.getId())
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .degree(dto.getDegree())
                .build();
    }
}
