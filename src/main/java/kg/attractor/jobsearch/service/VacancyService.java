package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies ();

    Page<VacancyDto> getActiveVacancies(int page);

    VacancyDto getVacancyById (int vacancyId);

    List<VacancyDto> getVacanciesByApplicant(int id);

    List<VacancyDto> getVacanciesByEmployer(Authentication auth);

    Page<VacancyDto> getVacanciesByCategory (Integer categoryId, int page);

    List<VacancyDto> getVacanciesByCategory (Integer categoryId);

    void createVacancy (VacancyCreateDto vacancyDto, Authentication auth);

    void updateVacancy (VacancyUpdateDto vacancyDto, Authentication auth);

    void deleteVacancy (int vacancyId, String email);
}
