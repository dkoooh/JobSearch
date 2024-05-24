package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.exception.ForbiddenException;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
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
    public Page<ResumeDto> getAllActive(int page) {
        List<ResumeDto> resumes = resumeRepository.findAllByIsActiveTrue().stream()
                .map(this::convertToDto)
                .toList();

        return toPage(resumes, PageRequest.of(page, 5));
    }

    @Override
    public ResumeDto getById(int resumeId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        ResumeDto resumeDto = convertToDto(
                resumeRepository.findById(resumeId)
                        .orElseThrow(() -> new NotFoundException("error.notFound.resume"))
        );

        if (!resumeDto.getApplicant().getEmail().equals(userEmail) && !resumeDto.getIsActive()) {
            throw new ForbiddenException("error.forbidden.resume");
        }

        return resumeDto;
    }

    @Override
    public List<ResumeDto> getAllActiveByCategory(int categoryId) {
        List<Resume> list = resumeRepository.findAllByCategoryIdAndIsActiveTrue(categoryId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Page<ResumeDto> getAllActiveByCategory(int categoryId, int page) {
        List<ResumeDto> list = resumeRepository.findAllByCategoryIdAndIsActiveTrue(categoryId).stream()
                .map(this::convertToDto)
                .toList();

        return toPage(list, PageRequest.of(page, 5));
    }

    @Override
    public Page<ResumeDto> getAllByApplicant(int applicantId, int page) {
        List<Resume> list = resumeRepository.findAllByAuthorId(applicantId);
        return toPage(list.stream()
                .map(this::convertToDto)
                .toList(), PageRequest.of(page, 6));
    }

    @Override
    public List<ResumeDto> getAllByApplicant(int applicantId) {
        List<Resume> list = resumeRepository.findAllByAuthorId(applicantId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    @Override
    public void create(ResumeCreateDto dto, Authentication auth){
        Resume resume = Resume.builder()
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.user")))
                .name(dto.getName())
                .category(categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.category")))
                .salary(dto.getSalary())
                .isActive(dto.getIsActive() != null ? dto.getIsActive() : false)
                .createdDate(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();


        int resumeId = resumeRepository.save(resume).getId();

        if (dto.getEducationInfo() != null) {
            dto.getEducationInfo().forEach(
                    eduInfoCreateDto -> eduInfoService.create(eduInfoCreateDto, resumeId)
            );
        }
        if (dto.getWorkExperienceInfo() != null) {
            dto.getWorkExperienceInfo().forEach(
                    workExpInfoCreateDto -> workExpInfoService.create(workExpInfoCreateDto, resumeId)
            );
        }
        dto.getContacts().forEach(
                contactInfoCreateDto -> contactInfoService.create(contactInfoCreateDto, resumeId)
        );
    }

    @Override
    public void edit(ResumeUpdateDto resumeDto, Authentication auth) {
        if (!resumeRepository.existsById(resumeDto.getId())) {
            throw new NotFoundException("error.notFound.resume");
        }

        Resume resume = Resume.builder()
                .id(resumeDto.getId())
                .author(userRepository.findByEmail(auth.getName())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.user")))
                .name(resumeDto.getName())
                .category(categoryRepository.findById(resumeDto.getCategoryId())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.category")))
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
    public void delete(int resumeId, String email){

        if (!resumeRepository.existsById(resumeId)) {
            throw new NotFoundException("error.notFound.resume");
        } if (!userService.getByEmail(email).getId().equals(getById(resumeId).getApplicant().getId())) {
            throw new ForbiddenException("error.forbidden.resume");
        }

        resumeRepository.deleteById(resumeId);
    }

    @Override
    public void update(int resumeId, String email) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new NotFoundException("error.notFound.resume");
        } if (!userService.getByEmail(email).getId().equals(getById(resumeId).getApplicant().getId())) {
            throw new ForbiddenException("error.forbidden.resume");
        }

        Resume resume = resumeRepository.findById(resumeId).orElseThrow();
        resume.setUpdateTime(LocalDateTime.now());
        resumeRepository.saveAndFlush(resume);
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

    private ResumeUpdateDto convertToUpdateDto (ResumeDto resumeDto) {
        return ResumeUpdateDto.builder()
                .id(resumeDto.getId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategory().getId()) // TODO DTO?
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive())
                .contacts(resumeDto.getContactInfos().stream()
                        .map(contactInfoService::convertToUpdateDto)
                        .toList())
                .educationInfo(resumeDto.getEducationInfo().stream()
                        .map(eduInfoService::convertToUpdateDto)
                        .toList())
                .workExperienceInfo(resumeDto.getWorkExperienceInfo().stream()
                        .map(workExpInfoService::convertToUpdateDto)
                        .toList())
                .build();
    }

    @Override
    public ResumeUpdateDto getUpdateDtoById(int id) {
        return convertToUpdateDto(getById(id));
    }

    private ResumeDto convertToDto(Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicant(userService.getByEmail(resume.getAuthor().getEmail()))
                .name(resume.getName())
                .category(categoryService.getById(resume.getCategory().getId()))
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
