package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("userRest")
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationDto userDto) {
        userService.create(userDto);
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UserUpdateDto dto, Authentication auth, @NotNull @PathVariable Integer id) {
        userService.update(auth, dto, id);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping("test")
    public ResponseEntity<?> checkUser (Authentication authentication) {
        var user = authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }


    @GetMapping("user/image")
    public ResponseEntity<?> downloadUserAvatar(Authentication auth) {
        return userService.downloadUserAvatar(auth.getName());
    }

    @PostMapping("user/image")
    public ResponseEntity<?> uploadUserAvatar(@NotNull MultipartFile file, Authentication auth) {
        userService.uploadUserAvatar(auth.getName(), file);
        return ResponseEntity.ok("User avatar was uploaded");
    }

    @GetMapping("employers/{employerEmail}")
    public ResponseEntity<?> getEmployer(@PathVariable @NotBlank @Email String employerEmail, Authentication auth) {
        return ResponseEntity.ok(userService.getEmployer(employerEmail, auth.getName()));
    }

    @GetMapping("applicants/{applicantEmail}")
    public ResponseEntity<?> getApplicant(Authentication auth, @PathVariable @NotBlank @Email String applicantEmail) {
        return ResponseEntity.ok(userService.getApplicant(auth.getName(), applicantEmail));
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<?> getApplicantsByVacancy(@PathVariable Integer vacancyId, Authentication auth) {
        return ResponseEntity.ok(userService.getApplicantsByVacancy(vacancyId, auth.getName()));
    }
}
