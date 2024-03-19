package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {
    List<Vacancy> getVacancies (String email) throws CustomException;

    List<Vacancy> getActiveVacancies ();

    Vacancy getVacancyById (int vacancyId);

    List<Vacancy> getVacanciesByUser (int id);

    List<Vacancy> getVacanciesByCategory (String email, Integer categoryId) throws CustomException;

    void createVacancy (VacancyDto vacancyDto) throws CustomException;

    void updateVacancy (int vacancyId, VacancyDto vacancyDto) throws CustomException;

    void deleteVacancy (int vacancyId, Integer authorId) throws CustomException;
}
