package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacancyService {
    Boolean exists(Integer vacancyId);

    List<VacancyDto> getAll();

    Page<VacancyDto> getActiveVacancies(int page);

    VacancyDto getById(int vacancyId);

    List<VacancyDto> getAllByApplicant(int id);

    List<VacancyDto> getAllByEmployer(Authentication auth);

    Page<VacancyDto> getAllActiveByCategory(Integer categoryId, int page);

    List<VacancyDto> getAllActiveByCategory(Integer categoryId);

    void create(VacancyCreateDto vacancyDto, Authentication auth);

    void update(VacancyUpdateDto vacancyDto, Authentication auth);

    void delete(int vacancyId, String email);
}
