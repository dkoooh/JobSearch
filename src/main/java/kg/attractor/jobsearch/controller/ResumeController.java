package kg.attractor.jobsearch.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @DeleteMapping("{resumeId}")
    public ResponseEntity<?> deleteResume (String email, @PathVariable Integer resumeId) {
            resumeService.deleteResume(resumeId, email);
            return ResponseEntity.ok("Resume is successfully deleted");
    }

    @PutMapping
    public ResponseEntity<?> updateResume (ResumeDto resumeDto) {
            resumeService.updateResume(resumeDto);
            return ResponseEntity.ok("Resume is successfully created");
    }

    @PostMapping
    public ResponseEntity<?> createResume (ResumeDto resume) {
            resumeService.createResume(resume);
            return ResponseEntity.ok("Resume is successfully created");
    }
}
