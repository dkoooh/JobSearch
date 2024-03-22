package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<?> createResume(@RequestBody @Valid ResumeCreateDto resumeDto) {
        resumeService.create(resumeDto);
        return ResponseEntity.ok("Resume is successfully created");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateResume(@RequestBody @Valid ResumeUpdateDto resumeDto) {
        resumeService.update(resumeDto);
        return ResponseEntity.ok("Resume is successfully updated");
    }

    @DeleteMapping("{resumeId}")
    public ResponseEntity<?> deleteResume(@NotBlank @Email String email, @PathVariable Integer resumeId) {
        resumeService.deleteResume(resumeId, email);
        return ResponseEntity.ok("Resume is successfully deleted");
    }

    @GetMapping
    public ResponseEntity<?> getResumes(@NotBlank @Email String employerEmail) {
        return ResponseEntity.ok(resumeService.getResumes(employerEmail));
    }

    @GetMapping("category={categoryId}")
    public ResponseEntity<?> getResumeByCategory(@PathVariable int categoryId, @NotBlank @Email String employerEmail) {
        return ResponseEntity.ok(resumeService.getResumesByCategory(categoryId, employerEmail));
    }


}
