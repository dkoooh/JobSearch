package kg.attractor.jobsearch.controller.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ResponseControllerApi")
@RequestMapping("api/responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping("vacancy/{vacancyId}")
    public ResponseEntity<?> getResponseByVacancy(@PathVariable Integer vacancyId, Authentication auth) {
        return ResponseEntity.ok(responseService.getResponseByVacancy(vacancyId, auth.getName()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(responseService.getById(id));
    }
}
