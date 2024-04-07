package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.vacancy.VacancyCreateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyUpdateDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoryService categoryService;

    @GetMapping("{id}")
    public String getVacancyById(@PathVariable int id, Model model) {
        VacancyDto vacancy = vacancyService.getVacancyById(id);
        model.addAttribute("vacancy", vacancy);
        model.addAttribute("author", userService.getUserById(vacancy.getAuthorId()));
        return "/vacancy/vacancy";
    }

    @GetMapping
    public String getVacancies(Model model) {
        List<VacancyDto> vacancies = vacancyService.getVacancies();
        model.addAttribute("vacancies", vacancies);
        return "vacancy/vacancies";
    }

    @GetMapping("create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "vacancy/create";
    }

    @PostMapping("create")
    public String create(VacancyCreateDto dto, Authentication auth) {
        vacancyService.createVacancy(dto, auth);
        return "redirect:/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("vacancy", vacancyService.getVacancyById(id));
        return "vacancy/edit";
    }

    @PostMapping("{id}/edit")
    public String edit(VacancyUpdateDto dto, Authentication auth) {
        vacancyService.updateVacancy(dto, auth);
        return "redirect:/users";
    }
}
