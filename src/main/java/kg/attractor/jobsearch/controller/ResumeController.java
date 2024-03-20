package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
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

    @GetMapping
    public ResponseEntity<?> getResumes (String employerEmail) {
        try {
            return ResponseEntity.ok(resumeService.getResumes(employerEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("category={categoryId}")
    public ResponseEntity<?> getResumeByCategory (@PathVariable int categoryId, String employerEmail) {
        try {
            return ResponseEntity.ok(resumeService.getResumesByCategory(categoryId, employerEmail));
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createResume (ResumeDto resumeDto) {
        try {
            resumeService.createResume(resumeDto);
            return ResponseEntity.ok("Resume is successfully created");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateResume (ResumeDto resumeDto) {
        try {
            resumeService.updateResume(resumeDto);
            return ResponseEntity.ok("Resume is successfully updated");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{resumeId}")
    public ResponseEntity<?> deleteResume (String email, @PathVariable Integer resumeId) {
        try {
            resumeService.deleteResume(resumeId, email);
            return ResponseEntity.ok("Resume is successfully deleted");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
