package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping()
    public ResponseEntity<?> getResponseByVacancy (Integer vacancyId, String email) {
        try {
            return ResponseEntity.ok(responseService.getResponseByVacancy(vacancyId, email));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
