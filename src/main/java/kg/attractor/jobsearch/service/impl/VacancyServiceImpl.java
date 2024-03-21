package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    @Override
    public List<VacancyDto> getVacancies(String email) throws CustomException{
        if (!userDao.isUserExists(email) || !"applicant".equalsIgnoreCase(userDao.getUserByEmail(email).get()
                .getAccountType())) {
            throw new CustomException("Access denied");
        }

        List<Vacancy> list = vacancyDao.getVacancies();

        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> getActiveVacancies() {
        List<Vacancy> list = vacancyDao.getActiveVacancies();

        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @SneakyThrows
    @Override
    public VacancyDto getVacancyById(int vacancyId) {
        return convertListToDto(
                vacancyDao.getVacancyById(vacancyId).orElseThrow(
                        () -> new CustomException("Not found")
                )
        );
    }

    @Override
    public List<VacancyDto> getVacanciesByUser(int id) {
        List<Vacancy> list = vacancyDao.getVacanciesByUser(id);
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String email, Integer categoryId) throws CustomException {
        if (categoryId == null || !categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList()
                .contains(categoryId)) {
            throw new CustomException("Invalid category ID");
        }

        if (!userDao.isUserExists(email) || !"applicant".equalsIgnoreCase(userDao.getUserByEmail(email).get()
                .getAccountType())) {
            throw new CustomException("Access denied");
        }

        List<Vacancy> list = vacancyDao.getVacanciesByCategory(categoryId);
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) throws CustomException {
            if (vacancyDto.getName() == null || vacancyDto.getName().isBlank()) {
                throw new CustomException("Name is empty");
            } else if (vacancyDto.getCategoryId() != null &&
                    !categoryDao.getCategories().stream()
                            .map(Category::getId)
                            .toList()
                            .contains(vacancyDto.getCategoryId())
            ) {
                throw new CustomException("Invalid category");
            } else if (vacancyDto.getAuthorEmail() == null) {
                throw new CustomException("Author is empty");
            } else if (!userDao.isUserExists(vacancyDto.getAuthorEmail())) {
                throw new CustomException("User is not exists");
            } else if (!"employer".equalsIgnoreCase(userDao.getUserByEmail(vacancyDto.getAuthorEmail())
                    .get().getAccountType())) {
                throw new CustomException("User is not employer");
            }

        Vacancy vacancy = Vacancy.builder()
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .categoryId(vacancyDto.getCategoryId())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(true)
                .authorId(userDao.getUserByEmail(
                        vacancyDto.getAuthorEmail()).get().getId()
                )
                .build();

        vacancyDao.createVacancy(vacancy);
    }

    @Override
    public void updateVacancy(VacancyDto vacancyDto) throws CustomException {
        if (vacancyDto.getName() == null || vacancyDto.getName().isBlank()) {
            throw new CustomException("Name is empty");
        } else if (vacancyDto.getCategoryId() != null &&
                !categoryDao.getCategories().stream()
                        .map(Category::getId)
                        .toList()
                        .contains(vacancyDto.getCategoryId())
        ) {
            throw new CustomException("Invalid category");
        } else if (vacancyDao.getVacancyById(vacancyDto.getId()).isEmpty()) {
            throw new CustomException("Invalid vacancy ID");
        } else if (userDao.getUserByEmail(vacancyDto.getAuthorEmail()).isEmpty()
                || !Objects.equals(
                        vacancyDao.getVacancyById(vacancyDto.getId()).get().getAuthorId(),
                        userDao.getUserByEmail(vacancyDto.getAuthorEmail()).get().getId()
                    )
        ) {
            throw new CustomException("You cannot change the vacancy of another employer");
        }

        Vacancy vacancy = Vacancy.builder()
                .id(vacancyDto.getId())
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .categoryId(vacancyDto.getCategoryId())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.getIsActive())
                .build();

        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void deleteVacancy(int vacancyId, Integer authorId) throws CustomException{
        if (vacancyDao.getVacancyById(vacancyId).isEmpty()) {
            throw new CustomException("Invalid ID");
        } else if (authorId == null || vacancyDao.getVacancyById(vacancyId).get().getAuthorId() != authorId) {
            throw new CustomException("You cannot delete the vacancy of another employer");
        }
        vacancyDao.deleteVacancy(vacancyId);
    }

    private VacancyDto convertListToDto (Vacancy vacancy) {
        return VacancyDto.builder()
                        .id(vacancy.getId())
                        .name(vacancy.getName())
                        .description(vacancy.getDescription())
                        .categoryId(vacancy.getCategoryId())
                        .salary(vacancy.getSalary())
                        .expFrom(vacancy.getExpFrom())
                        .expTo(vacancy.getExpTo())
                        .isActive(vacancy.getIsActive())
                        .authorId(vacancy.getAuthorId())
                        .createdDate(vacancy.getCreatedDate())
                        .updateTime(vacancy.getUpdateTime())
                        .build();
    }
}
