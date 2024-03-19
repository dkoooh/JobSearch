package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyDao vacancyDao;
    private final UserDao userDao;
    private final CategoryDao categoryDao;

    public List<Vacancy> getVacancies() {
        return vacancyDao.getVacancies();
    }

    public List<Vacancy> getActiveVacancies() {
        return vacancyDao.getActiveVacancies();
    }

    @SneakyThrows
    public Vacancy getVacancyById(int vacancyId) {
        return vacancyDao.getVacancyById(vacancyId).orElseThrow(() -> new CustomException("Not found"));
    }

    public List<Vacancy> getVacanciesByUser(int id) {
        return vacancyDao.getVacanciesByUser(id);
    }

    public List<Vacancy> getVacanciesByCategory(int id) {
        return vacancyDao.getVacanciesByCategory(id);
    }

    public void createVacancy(VacancyDto vacancyDto) throws CustomException{
        if (vacancyDto.getName() == null || vacancyDto.getName().isBlank()) {
            throw new CustomException("Name is empty");
        } else if ( vacancyDto.getCategoryId() != null &&
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
                .authorId(userDao.getUserByEmail(
                        vacancyDto.getAuthorEmail()).get().getId()
                )
                .build();

        vacancyDao.createVacancy(vacancy);
    }

    public void updateVacancy(Vacancy vacancy) {
        vacancyDao.updateVacancy(vacancy);
    }

    public void deleteVacancy(int vacancyId) {
        vacancyDao.deleteVacancy(vacancyId);
    }
}
