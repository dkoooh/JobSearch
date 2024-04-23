package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webmvc.core.fn.SpringdocRouteBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserService userService;
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
    public Page<VacancyDto> getActiveVacancies(int page) {
        List<Vacancy> list = vacancyDao.getActiveVacancies();

        List<VacancyDto> activeVacancies = list.stream()
                .map(this::convertListToDto)
                .toList();

        return toPage(activeVacancies, PageRequest.of(page, 5));
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
    public List<VacancyDto> getVacanciesByApplicant(int id) {
        List<Vacancy> list = vacancyDao.getVacanciesByApplicant(id);
        return list.stream()
                .map(this::convertListToDto)
                .toList();
    }

    public List<VacancyDto> getVacanciesByEmployer(Authentication auth) {
        List<Vacancy> list = vacancyDao.getVacanciesByEmployer(auth.getName());
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
    public Page<VacancyDto> getVacanciesByCategory(Integer categoryId, int page) {
        if (categoryId == null || !categoryDao.getCategories().stream()
                .map(Category::getId)
                .toList()
                .contains(categoryId)) {
            throw new CustomException("Invalid category ID");
        }

        List<Vacancy> list = vacancyDao.getVacanciesByCategory(categoryId);
        List<VacancyDto> vacancies = list.stream()
                .map(this::convertListToDto)
                .toList();

        return toPage(vacancies, PageRequest.of(page, 5));
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
                .isActive(vacancyDto.getIsActive() != null ? vacancyDto.getIsActive() : false)
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
                .isActive(vacancyDto.getIsActive() != null ? vacancyDto.getIsActive() : false)
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

    private Page<VacancyDto> toPage(List<VacancyDto> vacancies, Pageable pageable){
        if (pageable.getOffset() >= vacancies.size()){
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize() > vacancies.size() ?
                vacancies.size() : pageable.getOffset() + pageable.getPageSize()));
        List<VacancyDto> subList = vacancies.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, vacancies.size());
    }

    private VacancyDto convertListToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .category(categoryDao.getCategoryById(
                        vacancy.getCategoryId()).orElseThrow(
                                () -> new NotFoundException("Cannot find category with ID: " + vacancy.getCategoryId()))
                        .getName()
                )
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .author(userService.getUserById(vacancy.getAuthorId()))
                .createdDate(vacancy.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .updateTime(vacancy.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }
}
