package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController("resumeControllerRest")
@RequestMapping("api/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createResume(@RequestBody @Valid ResumeCreateDto resumeDto, Authentication auth) {
        resumeService.create(resumeDto, auth);
        return ResponseEntity.ok("Resume is successfully created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateResume(@RequestBody @Valid ResumeUpdateDto resumeDto, Authentication auth) {
        resumeService.update(resumeDto, auth);
        return ResponseEntity.ok("Resume is successfully updated");
    }

    @DeleteMapping("{resumeId}")
    public ResponseEntity<?> deleteResume(Authentication auth, @PathVariable Integer resumeId) {
        resumeService.delete(resumeId, auth.getName());
        return ResponseEntity.ok("Resume is successfully deleted");
    }

    @GetMapping
    public ResponseEntity<?> getResumes(Authentication auth) {
        return ResponseEntity.ok(resumeService.getAll(auth.getName()));
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getResumeByCategory(@PathVariable int categoryId) {
        return ResponseEntity.ok(resumeService.getAllActiveByCategory(categoryId));
    }

    @GetMapping("applicant")
    public ResponseEntity<?> getResumesByApplicant (Authentication auth) {
        return ResponseEntity.ok(resumeService.getAllByApplicant(userService.getByEmail(auth.getName()).getId()));
    }
}
