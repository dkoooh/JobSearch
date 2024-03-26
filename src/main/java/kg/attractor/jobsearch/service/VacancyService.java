package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies ();

    List<VacancyDto> getActiveVacancies (String email);

    VacancyDto getVacancyById (int vacancyId);

    List<VacancyDto> getVacanciesByUser (int id);

    List<VacancyDto> getVacanciesByCategory (String email, Integer categoryId);

    void createVacancy (VacancyCreateDto vacancyDto);

    void updateVacancy (VacancyUpdateDto vacancyDto);

    void deleteVacancy (int vacancyId, String email);
}
