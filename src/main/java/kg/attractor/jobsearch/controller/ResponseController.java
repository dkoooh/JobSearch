package kg.attractor.jobsearch.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping("{vacancyId}")
    public ResponseEntity<?> getResponseByVacancy(@PathVariable Integer vacancyId, @Email @NotBlank String email) {
        return ResponseEntity.ok(responseService.getResponseByVacancy(vacancyId, email));
    }
}
