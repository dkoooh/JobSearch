package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.EducationInfo;
import kg.attractor.jobsearch.service.EduInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EduInfoInfoServiceImpl implements EduInfoService {
    private final EducationInfoDao educationInfoDao;

    public void create(EduInfoCreateDto dto, int resumeId) {
        EducationInfo eduInfo = EducationInfo.builder()
                .resumeId(resumeId)
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .degree(dto.getDegree())
                .build();

        educationInfoDao.create(eduInfo);
    }

    @Override
    public void update(EduInfoUpdateDto dto, int resumeId) {
        EducationInfo eduInfo = EducationInfo.builder()
                .id(dto.getId())
                .resumeId(resumeId)
                .institution(dto.getInstitution())
                .program(dto.getProgram())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .degree(dto.getDegree())
                .build();

        if (eduInfo.getId() != null && educationInfoDao.getById(dto.getId()).isPresent()) {
            educationInfoDao.update(eduInfo);
        } else {
            educationInfoDao.create(eduInfo);
        }
    }

    @Override
    public EduInfoDto getById(int id, String applicantEmail) {
        EducationInfo eduInfo = educationInfoDao.getById(id).orElseThrow(() -> new CustomException("Not found"));

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
    public void delete(Integer id, String applicantEmail) {
        educationInfoDao.delete(id);
    }
}
