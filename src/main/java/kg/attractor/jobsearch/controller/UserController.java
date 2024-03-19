package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    public ResponseEntity<?> createUser (UserDto userDto) {
        try {
            service.createUser(userDto);
            return ResponseEntity.ok("User created successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("employer={employerEmail}")
    public ResponseEntity<?> getEmployer(@PathVariable String employerEmail, String applicantEmail) {
        try {
            return ResponseEntity.ok(service.getEmployer(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("applicant={applicantEmail}")
    public ResponseEntity<?> getApplicant (String employerEmail, @PathVariable String applicantEmail) {
        try {
            return ResponseEntity.ok(service.getApplicant(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
