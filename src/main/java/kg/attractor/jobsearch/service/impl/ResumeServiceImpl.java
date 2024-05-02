package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final WorkExpInfoService workExpInfoService;
    private final EduInfoService eduInfoService;
    private final ContactInfoService contactInfoService;
    private final UserService userService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ResumeDto> getAll(String employerEmail){
        return resumeRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Page<ResumeDto> getActiveResumes (int page) {
        List<ResumeDto> resumes = resumeRepository.findAllByIsActive(true).stream()
                .map(this::convertToDto)
                .toList();

        return toPage(resumes, PageRequest.of(page, 5));
    }

    private Page<ResumeDto> toPage(List<ResumeDto> resumes, Pageable pageable){
        if (pageable.getOffset() >= resumes.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize() > resumes.size() ?
                resumes.size() : pageable.getOffset() + pageable.getPageSize()));
        List<ResumeDto> subList = resumes.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, resumes.size());
    }

    @Override
    public ResumeDto getById(int resumeId) {
        return convertToDto(
                resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new NotFoundException("Cannot find resume with ID: " + resumeId))
        );
    }

    @Override
    public List<ResumeDto> getResumesByCategory(int categoryId, String email){

        List<Resume> list = resumeRepository.findAllByCategoryId(categoryId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant(int applicantId) {
        List<Resume> list = resumeRepository.findAllByAuthorId(applicantId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void create(ResumeCreateDto dto, Authentication auth){
        if (!categoryService.isExists(dto.getCategoryId())) {
            throw new NotFoundException("Invalid category");
        }

        Resume resume = Resume.builder()
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new NotFoundException("Cannot find user with email: " + auth.getName())))
                .name(dto.getName())
                .category(categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new NotFoundException("Cannot find category by ID:" + dto.getCategoryId())))
                .salary(dto.getSalary())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : false)
                .createdDate(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();


        int resumeId = resumeRepository.save(resume).getId();

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
        if (!categoryService.isExists(resumeDto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        if (!resumeRepository.existsById(resumeDto.getId())) {
            throw new CustomException("Cannot find resume with ID: " + resumeDto.getId());
        }

        Resume resume = Resume.builder()
                .id(resumeDto.getId())
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new NotFoundException("Cannot find user with email: " + auth.getName())))
                .name(resumeDto.getName())
                .category(categoryRepository.findById(resumeDto.getCategoryId())
                        .orElseThrow(() -> new NotFoundException("Cannot find category by ID:" + resumeDto.getCategoryId())))
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive() != null ? resumeDto.getIsActive() : Boolean.FALSE)
                .createdDate(LocalDateTime.parse(getById(resumeDto.getId()).getCreatedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updateTime(LocalDateTime.now())
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

        resumeRepository.save(resume);
    }

    @Override
    public void deleteResume(int resumeId, String email){

        if (!resumeRepository.existsById(resumeId)) {
            throw new NotFoundException("Cannot find resume with ID: " + resumeId);
        } if (!userService.getByEmail(email).getId().equals(getById(resumeId).getApplicant().getId())) {
            throw new CustomException("Access denied");
        }

        resumeRepository.deleteById(resumeId);
    }

    private ResumeDto convertToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicant(userService.getByEmail(resume.getAuthor().getEmail()))
                .name(resume.getName())
                .category(categoryService.getById(resume.getCategory().getId()).getName()) // TODO CategoryDto?
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .educationInfo(eduInfoService.getAllByResumeId(resume.getId()))
                .workExperienceInfo(workExpInfoService.getAllByResumeId(resume.getId()))
                .contactInfos(contactInfoService.getAllByResumeId(resume.getId()))
                .createdDate(resume.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updateTime(resume.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
