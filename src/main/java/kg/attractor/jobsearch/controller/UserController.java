package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationDto userDto) {
        userService.create(userDto);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateDto userDto) {
        userService.update(userDto);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping("{userEmail}/image")
    public ResponseEntity<?> downloadUserAvatar(@PathVariable @NotBlank @Email String userEmail) {
        return userService.downloadUserAvatar(userEmail);
    }

    @GetMapping("employers/{employerEmail}")
    public ResponseEntity<?> getEmployer(@PathVariable @NotBlank @Email String employerEmail, @NotBlank @Email String applicantEmail) {
        return ResponseEntity.ok(userService.getEmployer(employerEmail, applicantEmail));
    }

    @GetMapping("applicants/{applicantEmail}")
    public ResponseEntity<?> getApplicant(@NotBlank @Email String employerEmail, @PathVariable @NotBlank @Email String applicantEmail) {
        return ResponseEntity.ok(userService.getApplicant(employerEmail, applicantEmail));
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<?> getApplicantsByVacancy(@PathVariable Integer vacancyId, @NotBlank @Email String email) {
        return ResponseEntity.ok(userService.getApplicantsByVacancy(vacancyId, email));
    }
}
