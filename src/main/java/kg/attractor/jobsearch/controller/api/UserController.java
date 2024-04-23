package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("userControllerRest")
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

    @GetMapping("user/image/{email}")
    public ResponseEntity<?> downloadUserAvatar(@PathVariable String email) {
        return userService.downloadUserAvatar(email);
    }

    @PostMapping("user/image")
    public ResponseEntity<?> uploadUserAvatar(@NotNull MultipartFile file, Authentication auth) {
        userService.uploadUserAvatar(auth.getName(), file);
        return ResponseEntity.ok("User avatar was uploaded");
    }

    @GetMapping("employers/{employerId}")
    public ResponseEntity<?> getEmployer(@PathVariable @NotNull Integer employerId) {
        return ResponseEntity.ok(userService.getEmployer(employerId));
    }

    @GetMapping("applicants/{applicantId}")
    public ResponseEntity<?> getApplicant(@PathVariable @NotNull Integer applicantId) {
        return ResponseEntity.ok(userService.getApplicant(applicantId));
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<?> getApplicantsByVacancy(@PathVariable Integer vacancyId, Authentication auth) {
        return ResponseEntity.ok(userService.getApplicantsByVacancy(vacancyId, auth.getName()));
    }
}
