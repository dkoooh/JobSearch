package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<?> createVacancy (VacancyDto vacancyDto) {
        try {
            vacancyService.createVacancy(vacancyDto);
            return ResponseEntity.ok("Vacancy is successfully created");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateVacancy (VacancyDto vacancyDto) {
        try {
            vacancyService.updateVacancy(vacancyDto);
            return ResponseEntity.ok("Vacancy is successfully updated");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
