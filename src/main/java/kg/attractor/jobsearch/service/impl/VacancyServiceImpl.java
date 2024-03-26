package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
    public List<VacancyDto> getVacancies() {
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
    public List<VacancyDto> getVacanciesByCategory(Integer categoryId) {
        if (categoryId == null || !categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList()
                .contains(categoryId)) {
            throw new CustomException("Invalid category ID");
        }

        List<Vacancy> list = vacancyDao.getVacanciesByCategory(categoryId);
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    @Override
    public void createVacancy(VacancyCreateDto vacancyDto, Authentication auth){
        if (!categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList()
                .contains(vacancyDto.getCategoryId())) {
            throw new CustomException("Invalid category");
        }

        Vacancy vacancy = Vacancy.builder()
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .categoryId(vacancyDto.getCategoryId())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.getIsActive() != null ? vacancyDto.getIsActive() : true)
                .authorId(userDao.getUserByEmail(
                        auth.getName()).get().getId()
                )
                .build();

        vacancyDao.createVacancy(vacancy);
    }

    @Override
    public void updateVacancy(VacancyUpdateDto vacancyDto, Authentication auth)  {
        if (!categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList()
                .contains(vacancyDto.getCategoryId())
        ) {
            throw new CustomException("Invalid category");
        } else if (!Objects.equals(
                vacancyDao.getVacancyById(vacancyDto.getId()).get().getAuthorId(),
                userDao.getUserByEmail(auth.getName()).get().getId()
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
                .isActive(vacancyDto.getIsActive() != null ? vacancyDto.getIsActive() : true)
                .build();

        vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public void deleteVacancy(int vacancyId, String email)  {
        if (!Objects.equals(
                vacancyDao.getVacancyById(vacancyId).get().getAuthorId(),
                userDao.getUserByEmail(email).get().getId())) {
            throw new CustomException("You cannot delete the vacancy of another employer");
        }
        vacancyDao.deleteVacancy(vacancyId);
    }

    private VacancyDto convertListToDto(Vacancy vacancy) {
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
