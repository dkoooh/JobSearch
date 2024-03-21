package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeDao resumeDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public List<ResumeDto> getResumes (String employerEmail) throws CustomException{
        Utils.verifyUser(employerEmail, "employer", userDao);

        return resumeDao.getResumes().stream()
                .map(this::convertToDto)
                .toList();
    }

    @SneakyThrows
    @Override
    public ResumeDto getResumeById (int resumeId) {
        return convertToDto(
                resumeDao.getResumeById(resumeId)
                        .orElseThrow(() -> new CustomException("Cannot find resume with ID: " + resumeId))
        );
    }

    @Override
    public List<ResumeDto> getResumesByCategory (int categoryId, String email) throws CustomException {
        Utils.verifyUser(email, "employer", userDao);

        List<Resume> list = resumeDao.getResumesByCategory(categoryId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> getResumesByApplicant (int applicantId) {
        List<Resume> list = resumeDao.getResumesByApplicant(applicantId);
        return list.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void createResume (ResumeDto resumeDto) throws CustomException{
        Utils.verifyUser(resumeDto.getApplicantEmail(), "applicant", userDao);

        if (resumeDto.getName() == null || resumeDto.getName().isBlank()) {
            throw new CustomException("Name is empty");
        } else if (resumeDto.getCategoryId() != null && !categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList().contains(resumeDto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        Resume resume = Resume.builder()
                .applicantId(userDao.getUserByEmail(resumeDto.getApplicantEmail()).get().getId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(true)
                .build();

        resumeDao.create(resume);
    }

    @Override
    public void updateResume (ResumeDto resumeDto) throws CustomException{
        Utils.verifyUser(resumeDto.getApplicantEmail(), "applicant", userDao);

        if (resumeDto.getName() == null || resumeDto.getName().isBlank()) {
            throw new CustomException("Name is empty");
        } else if (resumeDto.getCategoryId() != null && !categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList().contains(resumeDto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        if (!resumeDao.getResumes().stream().map(Resume::getId).toList().contains(resumeDto.getId())) {
            throw new CustomException("Cannot find resume with ID: " + resumeDto.getId());
        }

        Resume resume = Resume.builder()
                .id(resumeDto.getId())
                .applicantId(userDao.getUserByEmail(resumeDto.getApplicantEmail()).get().getId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive() != null ? resumeDto.getIsActive() : true)
                .build();

        resumeDao.updateResume(resume);
    }

    @Override
    public void deleteResume(int resumeId, String email) throws CustomException{
        Utils.verifyUser(email, "applicant", userDao);

        if (!resumeDao.getResumes().stream().map(Resume::getId).toList().contains(resumeId)) {
            throw new CustomException("Cannot find resume with ID: " + resumeId);
        }

        resumeDao.deleteResume(resumeId);
    }

    private ResumeDto convertToDto (Resume resume) {
        return ResumeDto.builder()
                .id(resume.getId())
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }

    // TODO
//      - Добавить инфу об образоании и опыте
//      - Сделать тип профиля неизменияемым
//      - Поработать с исключениями
//      - Сделать добавление аватарки
//      - Что имелось в виду под "Поиск резюме по должности"
//
}
