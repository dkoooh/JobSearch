package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String create(ResumeCreateDto resumeCreateDto) {
        System.out.println(resumeCreateDto);
        return "redirect:/";
    }

}
