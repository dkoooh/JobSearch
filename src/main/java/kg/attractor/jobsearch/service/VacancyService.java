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

    Page<VacancyDto> getAllActive(Integer page, Integer categoryId, String sortedBy, String search);

    Page<VacancyDto> getAllActive();

    VacancyDto getById(int vacancyId);

    VacancyUpdateDto getUpdateDtoById(int vacancyId);

    List<VacancyDto> getAllByApplicant(int id);

    Page<VacancyDto> getAllByEmployer(String email, int page);

    List<VacancyDto> getAllByEmployer(Authentication auth);

    List<VacancyDto> getAllActiveByCategory(Integer categoryId);

    void create(VacancyCreateDto vacancyDto, Authentication auth);

    void edit(VacancyUpdateDto vacancyDto, Authentication auth);

    void delete(int vacancyId, String email);

    void update(int vacancyId, String email);
}
