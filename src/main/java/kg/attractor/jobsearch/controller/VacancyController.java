package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<?> getVacancies(@NotBlank @Email String email) {
        return ResponseEntity.ok(vacancyService.getVacancies(email));
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getVacanciesByCategory(@PathVariable Integer categoryId, @NotBlank @Email String email) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(email, categoryId));
    }

    @PostMapping
    public ResponseEntity<?> createVacancy(@RequestBody @Valid VacancyCreateDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return ResponseEntity.ok("Vacancy is successfully created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateVacancy(@RequestBody @Valid VacancyUpdateDto vacancyDto) {
        vacancyService.updateVacancy(vacancyDto);
        return ResponseEntity.ok("Vacancy is successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVacancy(@PathVariable int id, @NotBlank @Email String authorEmail) {
        vacancyService.deleteVacancy(id, authorEmail);
        return ResponseEntity.ok("Vacancy is successfully deleted");
    }
}
