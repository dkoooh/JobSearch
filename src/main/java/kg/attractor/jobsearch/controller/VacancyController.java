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
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        model.addAttribute("vacancies", vacancies);
        model.addAttribute("categories", categories);
        return "vacancy/vacancies";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("vacancyCreateDto", new VacancyCreateDto());
        return "vacancy/create";
    }

    @PostMapping("create")
    public String create(@Valid VacancyCreateDto dto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("vacancyCreateDto", dto);
            model.addAttribute("categories", categoryService.getAll());
            return "vacancy/create";
        }

        vacancyService.create(dto, auth);
        return "redirect:/account/profile";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("vacancyUpdateDto", vacancyService.getUpdateDtoById(id));
        return "vacancy/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(@Valid VacancyUpdateDto dto, BindingResult result, Model model, Authentication auth) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("vacancyUpdateDto", dto);
            System.out.println(dto.getId());
            return "vacancy/edit";
        }

        vacancyService.edit(dto, auth);
        return "redirect:/account/profile";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable (name = "id") Integer vacancyId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        vacancyService.update(vacancyId, userEmail);

        return "redirect:/account/profile";
    }
}
