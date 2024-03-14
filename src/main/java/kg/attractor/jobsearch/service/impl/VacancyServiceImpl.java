package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.exception.CustomException;
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

    public List<Vacancy> getVacancies () {
        return vacancyDao.getVacancies();
    }

    @SneakyThrows
    public Vacancy getVacancyById (int vacancyId) {
        return vacancyDao.getVacancyById(vacancyId).orElseThrow(() -> new CustomException("Not found"));
    }

    public List<Vacancy> getVacanciesByUser (int id) {
        return vacancyDao.getVacanciesByUser(id);
    }

    public List<Vacancy> getVacanciesByCategory (int id) {
        return vacancyDao.getVacanciesByCategory(id);
    }

    public void createVacancies (Vacancy vacancy) {
        vacancyDao.createVacancy(vacancy);
    }

    public void updateVacancy (Vacancy vacancy) {
        vacancyDao.updateVacancy(vacancy);
    }

    public void deleteVacancy (int vacancyId) {
        vacancyDao.deleteVacancy(vacancyId);
    }
}
