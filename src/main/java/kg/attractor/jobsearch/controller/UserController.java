package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser (UserCreationDto userDto) {
        try {
            userService.create(userDto);
            return ResponseEntity.ok("User created successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser (UserUpdateDto userDto) {
        try {
            userService.update(userDto);
            return ResponseEntity.ok("User updated successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{userEmail}/image")
    public ResponseEntity<?> downloadUserAvatar (@PathVariable String userEmail) {
        try {
            return userService.downloadUserAvatar (userEmail);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("employers/{employerEmail}")
    public ResponseEntity<?> getEmployer(@PathVariable String employerEmail, String applicantEmail) {
        try {
            return ResponseEntity.ok(userService.getEmployer(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("applicants/{applicantEmail}")
    public ResponseEntity<?> getApplicant (String employerEmail, @PathVariable String applicantEmail) {
        try {
            return ResponseEntity.ok(userService.getApplicant(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<?> getApplicantsByVacancy (@PathVariable Integer vacancyId, String email) {
        try {
            return ResponseEntity.ok(userService.getApplicantsByVacancy(vacancyId, email));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
