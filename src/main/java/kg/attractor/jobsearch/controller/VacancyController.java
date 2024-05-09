package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
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
    public String getVacancies(Model model,
                               @RequestParam(name = "p", defaultValue = "1") Integer page,
                               @RequestParam(name = "c", required = false) Integer categoryId,
                               @RequestParam(name = "sb", required = false) String sortedBy,
                               @RequestParam(name = "s", required = false, defaultValue = "") String search) {

        Page<VacancyDto> vacancies = vacancyService.getAllActive(page - 1, categoryId, sortedBy, search);
        List<CategoryDto> categories = categoryService.getAll();

        if (search == null) {
            search = "";
        }

        String attributes;
        if (categoryId != null) {
            if (sortedBy != null && !sortedBy.isBlank()) {
                attributes = String.format("c=%s&sb=%s&s=%s&", categoryId, sortedBy, search);
                model.addAttribute("attributes", attributes);
            } else {
                attributes = String.format("c=%s&s=%s&", categoryId, search);
                model.addAttribute("attributes", attributes);
            }
        } else {
            if (sortedBy != null && !sortedBy.isBlank()) {
                attributes = String.format("sb=%s&s=%s&", sortedBy, search);
                model.addAttribute("attributes", attributes);
            } else {
                attributes = String.format("s=%s&", search);
                model.addAttribute("attributes", attributes);
            }
        }

        model.addAttribute("page", page);
        model.addAttribute("vacancies", vacancies);
        model.addAttribute("categories", categories);
        return "vacancy/vacancies";
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
