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

    Page<VacancyDto> getAllActive(Integer categoryId, String sortedBy, Integer page);

    VacancyDto getById(int vacancyId);

    List<VacancyDto> getAllByApplicant(int id);

    Page<VacancyDto> getAllByEmployer(Authentication auth, int page);

    List<VacancyDto> getAllByEmployer(Authentication auth);

    List<VacancyDto> getAllActiveByCategory(Integer categoryId);

    void create(VacancyCreateDto vacancyDto, Authentication auth);

    void update(VacancyUpdateDto vacancyDto, Authentication auth);

    void delete(int vacancyId, String email);
}
