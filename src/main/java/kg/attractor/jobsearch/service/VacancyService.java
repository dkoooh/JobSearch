package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies ();

    List<VacancyDto> getActiveVacancies ();

    VacancyDto getVacancyById (int vacancyId);

    List<VacancyDto> getVacanciesByUser (int id);

    List<VacancyDto> getVacanciesByCategory (Integer categoryId);

    void createVacancy (VacancyCreateDto vacancyDto, Authentication auth);

    void updateVacancy (VacancyUpdateDto vacancyDto, Authentication auth);

    void deleteVacancy (int vacancyId, String email);
}
