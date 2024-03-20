package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies (String email) throws CustomException;

    List<VacancyDto> getActiveVacancies ();

    VacancyDto getVacancyById (int vacancyId);

    List<VacancyDto> getVacanciesByUser (int id);

    List<VacancyDto> getVacanciesByCategory (String email, Integer categoryId) throws CustomException;

    void createVacancy (VacancyDto vacancyDto) throws CustomException;

    void updateVacancy (VacancyDto vacancyDto) throws CustomException;

    void deleteVacancy (int vacancyId, Integer authorId) throws CustomException;
}
