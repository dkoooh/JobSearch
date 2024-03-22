package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
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
    public ResponseEntity<?> createResume (@RequestBody ResumeCreateDto resumeDto) {
        try {
            resumeService.create(resumeDto);
            return ResponseEntity.ok("Resume is successfully created");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateResume (@RequestBody ResumeUpdateDto resumeDto) {
        try {
            resumeService.update(resumeDto);
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


}
