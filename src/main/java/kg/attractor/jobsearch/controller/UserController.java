package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser (UserDto userDto) {
        try {
            service.updateUser(userDto);
            return ResponseEntity.ok("User updated successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{userEmail}/image")
    public ResponseEntity<?> uploadUserAvatar (@PathVariable String userEmail, MultipartFile userImage) {
        try {
            service.uploadUserAvatar (userEmail, userImage);
            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("{userEmail}/image")
    public ResponseEntity<?> downloadUserAvatar (@PathVariable String userEmail) {
        try {
            return service.downloadUserAvatar (userEmail);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("employers/{employerEmail}")
    public ResponseEntity<?> getEmployer(@PathVariable String employerEmail, String applicantEmail) {
        try {
            return ResponseEntity.ok(service.getEmployer(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("applicants/{applicantEmail}")
    public ResponseEntity<?> getApplicant (String employerEmail, @PathVariable String applicantEmail) {
        try {
            return ResponseEntity.ok(service.getApplicant(employerEmail, applicantEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping("vacancies/{vacancyId}")
    public ResponseEntity<?> getApplicantsByVacancy (@PathVariable Integer vacancyId, String email) {
        try {
            return ResponseEntity.ok(service.getApplicantsByVacancy(vacancyId, email));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
