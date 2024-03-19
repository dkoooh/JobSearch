package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("{id}")
    public ResponseEntity<?> updateVacancy (@PathVariable int id, VacancyDto vacancyDto) {
        try {
            vacancyService.updateVacancy(id, vacancyDto);
            return ResponseEntity.ok("Vacancy is successfully updated");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVacancy (@PathVariable int id, Integer authorId) {
        try {
            vacancyService.deleteVacancy(id, authorId);
            return ResponseEntity.ok("Vacancy is successfully deleted");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
