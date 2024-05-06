package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

    @GetMapping("{id}")
    public String getVacancyById(@PathVariable int id, Model model) {
        VacancyDto vacancy = vacancyService.getById(id);
        model.addAttribute("vacancy", vacancy);
        return "/vacancy/vacancy";
    }

    @GetMapping
    public String getVacancies(Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) {
        Page<VacancyDto> vacancies = vacancyService.getActiveVacancies(page - 1);
        List<CategoryDto> categories = categoryService.getAll();

        model.addAttribute("page", page);
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("categories", categories);
        return "vacancy/vacancies";
    }

    @GetMapping("categories")
    public String getVacanciesByCategory (@NotNull @RequestParam Integer categoryId,
                                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                                          Model model) {
        Page<VacancyDto> vacancies = vacancyService.getAllActiveByCategory(categoryId, page - 1);
        List<CategoryDto> categories = categoryService.getAll();

        model.addAttribute("page", page);
        model.addAttribute("categories", categories);
        model.addAttribute("vacancies", vacancies);

        return "/vacancy/vacancies";
    }


    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "vacancy/create";
    }

    @PostMapping("create")
    public String create(@Valid VacancyCreateDto dto, Authentication auth) {
        vacancyService.create(dto, auth);
        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("vacancy", vacancyService.getById(id));
        return "vacancy/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(@Valid VacancyUpdateDto dto, Authentication auth) {
        vacancyService.update(dto, auth);
        return "redirect:/users";
    }
}
