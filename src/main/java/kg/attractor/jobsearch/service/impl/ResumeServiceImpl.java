package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ContactInfoService;
import kg.attractor.jobsearch.service.EduInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.WorkExpInfoService;
import kg.attractor.jobsearch.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final WorkExpInfoService workExpInfoService;
    private final EduInfoService eduInfoService;
    private final ContactInfoService contactInfoService;

    @Override
    public List<ResumeDto> getResumes(String employerEmail){
        Utils.verifyUser(employerEmail, "employer", userDao);

        return resumeDao.getResumes().stream()
                .map(this::convertToDto)
                .toList();
    }

    @SneakyThrows
    @Override
    public ResumeDto getResumeById(int resumeId) {
        return convertToDto(
                resumeDao.getResumeById(resumeId)
                        .orElseThrow(() -> new CustomException("Cannot find resume with ID: " + resumeId))
        );
    }

    @Override
    public List<ResumeDto> getResumesByCategory(int categoryId, String email){

        List<Resume> list = resumeDao.getResumesByCategory(categoryId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant(int applicantId) {
        List<Resume> list = resumeDao.getResumesByApplicant(applicantId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void create(ResumeCreateDto dto, Authentication auth){
        if (!categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList().contains(dto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        Resume resume = Resume.builder()
                .applicantId(userDao.getUserByEmail(auth.getName()).get().getId())
                .name(dto.getName())
                .categoryId(dto.getCategoryId())
                .salary(dto.getSalary())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : false)
                .build();

        int resumeId = resumeDao.create(resume);

        dto.getEducationInfo().forEach(
                eduInfoCreateDto -> eduInfoService.create(eduInfoCreateDto, resumeId)
        );
        dto.getWorkExperienceInfo().forEach(
                workExpInfoCreateDto -> workExpInfoService.create(workExpInfoCreateDto, resumeId)
        );
        dto.getContacts().forEach(
                contactInfoCreateDto -> contactInfoService.create(contactInfoCreateDto, resumeId)
        );
    }

    @Override
    public void update(ResumeUpdateDto resumeDto, Authentication auth) {
        if (!categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList().contains(resumeDto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        if (!resumeDao.getResumes().stream().map(Resume::getId).toList().contains(resumeDto.getId())) {
            throw new CustomException("Cannot find resume with ID: " + resumeDto.getId());
        }

        Resume resume = Resume.builder()
                .id(resumeDto.getId())
                .applicantId(userDao.getUserByEmail(auth.getName()).get().getId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive() != null ? resumeDto.getIsActive() : false)
                .build();

        resumeDto.getEducationInfo().forEach(
                eduInfoUpdateDto -> eduInfoService.update(eduInfoUpdateDto, resume.getId())
        );
        resumeDto.getWorkExperienceInfo().forEach(
                workExpInfoUpdateDto -> workExpInfoService.update(workExpInfoUpdateDto, resume.getId())
        );
        resumeDto.getContacts().forEach(
                contactInfoUpdateDto -> contactInfoService.update(contactInfoUpdateDto, resume.getId())
        );

        resumeDao.updateResume(resume);
    }

    @Override
    public void deleteResume(int resumeId, String email){

        if (!resumeDao.getResumes().stream().map(Resume::getId).toList().contains(resumeId)) {
            throw new CustomException("Cannot find resume with ID: " + resumeId);
        } if (!userDao.getUserByEmail(email).get().getId().equals(resumeDao.getResumeById(resumeId).get().getApplicantId())) {
            throw new CustomException("Access denied");
        }

        resumeDao.deleteResume(resumeId);
    }

    private ResumeDto convertToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .category(categoryDao.getCategoryById(
                                resume.getCategoryId()).orElseThrow(
                                () -> new NotFoundException("Cannot find category with ID: " + resume.getCategoryId()))
                        .getName())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .educationInfo(eduInfoService.getByResumeId(resume.getId()))
                .workExperienceInfo(workExpInfoService.getByResumeId(resume.getId()))
                .contactInfos(contactInfoService.getByResumeId(resume.getId()))
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }
}
