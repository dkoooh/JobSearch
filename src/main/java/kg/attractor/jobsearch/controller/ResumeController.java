package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.resume.ResumeCreateDto;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.resume.ResumeUpdateDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final CategoryService categoryService;

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable int id) {
        ResumeDto resume = resumeService.getById(id);

        model.addAttribute("resume", resume);
        return "/resume/resume";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("resumeCreateDto", new ResumeCreateDto());
        return "/resume/create";
    }

    @PostMapping("create")
//    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String create(@Valid ResumeCreateDto resumeCreateDto,
                         BindingResult bindingResult,
                         Model model,
                         Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("resumeCreateDto", resumeCreateDto);
            model.addAttribute("categories", categoryService.getAll());
            return "resume/create";
        }

        System.out.println(resumeCreateDto);
        resumeService.create(resumeCreateDto, authentication);
        return "redirect:/account/profile";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("resumeUpdateDto", resumeService.getUpdateDtoById(id));
        return "resume/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(@Valid ResumeUpdateDto dto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("resumeUpdateDto", dto);
            return "resume/edit";
        }

        resumeService.edit(dto, auth);
        return "redirect:/account/profile";
    }

    @PostMapping("{id}/update")
    public String update (@PathVariable (name = "id") Integer resumeId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        resumeService.update(resumeId, userEmail);
        return "redirect:/account/profile";
    }


    @GetMapping
    public String getAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        Page<ResumeDto> resumes = resumeService.getAllActive(page - 1);

        model.addAttribute("page", page);
        model.addAttribute("categories", categories);
        model.addAttribute("resumes", resumes);

        return "resume/resumes";
    }

    @GetMapping("categories")
    public String getAllByCategory(@RequestParam(name = "page", defaultValue = "1") int page,
                                   @RequestParam int categoryId,
                                   Model model) {
        List<CategoryDto> categories = categoryService.getAll();
        Page<ResumeDto> resumes = resumeService.getAllActiveByCategory(categoryId, page - 1);

        model.addAttribute("page", page);
        model.addAttribute("categories", categories);
        model.addAttribute("resumes", resumes);

        return "resume/resumes";
    }

}
