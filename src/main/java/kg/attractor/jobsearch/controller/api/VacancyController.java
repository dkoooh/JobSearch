package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController("vacancyControllerRest")
@RequiredArgsConstructor
@RequestMapping("api/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public ResponseEntity<?> getVacancies(
            @RequestParam(name = "s", defaultValue = "", required = false) String search,
            @RequestParam(name = "c", required = false) Integer categoryId,
            @RequestParam(name = "sb", required = false) String sortedBy,
            @RequestParam(name = "p", defaultValue = "1") Integer page) {
        return ResponseEntity.ok(vacancyService.getAllActive(page - 1, categoryId, sortedBy, search));
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getVacanciesByCategory(@PathVariable @NotNull Integer categoryId) {
        return ResponseEntity.ok(vacancyService.getAllActiveByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<?> createVacancy(@RequestBody @Valid VacancyCreateDto vacancyDto, Authentication auth) {
        vacancyService.create(vacancyDto, auth);
        return ResponseEntity.ok("Vacancy is successfully created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateVacancy(@RequestBody @Valid VacancyUpdateDto vacancyDto, Authentication auth) {
        vacancyService.edit(vacancyDto, auth);
        return ResponseEntity.ok("Vacancy is successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVacancy(@PathVariable @NotNull int id, Authentication auth) {
        vacancyService.delete(id, auth.getName());
        return ResponseEntity.ok("Vacancy is successfully deleted");
    }

    @GetMapping("employer")
    public ResponseEntity<?> getVacanciesByEmployer(Authentication auth) {
        return ResponseEntity.ok(vacancyService.getAllByEmployer(auth));
    }
}
