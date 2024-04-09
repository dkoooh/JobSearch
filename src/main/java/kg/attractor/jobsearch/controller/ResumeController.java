package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable int id) {
        ResumeDto resume = resumeService.getResumeById(id);

        model.addAttribute("resume", resume);
        model.addAttribute("author", userService.getUserById(resume.getApplicantId()));
        return "/resume/resume";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "/resume/create";
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String create(ResumeCreateDto resumeCreateDto, Authentication authentication) {
        resumeService.create(resumeCreateDto, authentication);
        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("resume", resumeService.getResumeById(id));
        return "resume/edit";
    }

    @PostMapping("{id}/edit")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String edit(ResumeUpdateDto dto, Authentication auth) {
        resumeService.update(dto, auth);
        return "redirect:/users";
    }

    @GetMapping
    public String getActiveResumes (@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        List<Category> categories = categoryService.getCategories();
        Page<ResumeDto> resumes = resumeService.getActiveResumes(page - 1);

        model.addAttribute("page", page);
        model.addAttribute("categories", categories);
        model.addAttribute("resumes", resumes);

        return "resume/resumes";
    }

}
