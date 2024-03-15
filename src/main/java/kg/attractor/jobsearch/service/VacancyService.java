package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {
    List<Vacancy> getVacancies ();

    List<Vacancy> getActiveVacancies ();

    Vacancy getVacancyById (int vacancyId);

    List<Vacancy> getVacanciesByUser (int id);

    List<Vacancy> getVacanciesByCategory (int id);

    void createVacancies (Vacancy vacancy);

    void updateVacancy (Vacancy vacancy);

    void deleteVacancy (int vacancyId);
}
