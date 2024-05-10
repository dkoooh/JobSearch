package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @GetMapping("register")
    public String create(Model model) {
        model.addAttribute("userCreationDto", new UserCreationDto());
        return "user/register";
    }

    @PostMapping("register")
    public String create(@Valid UserCreationDto dto, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            userService.create(dto);
            return "redirect:/login";
        }
        model.addAttribute("userCreationDto", dto);
        return "user/register";
    }

    @GetMapping("applicant/{email}")
    public String getApplicant(@PathVariable String email,
                               Model model,
                               @RequestParam(name = "page", defaultValue = "1") int page) {
        UserDto applicant = userService.getApplicant(email);
        Page<ResumeDto> userResumes = resumeService.getAllByApplicant(applicant.getId(), page - 1);

        model.addAttribute("userResumes", userResumes);
        model.addAttribute("user", applicant);
        return "/user/profile";
    }

    @GetMapping("employer/{email}")
    public String getEmployer(@PathVariable String email, Model model,
                              @RequestParam(name = "page", defaultValue = "1") int page) {
        UserDto employer = userService.getEmployer(email);
        Page<VacancyDto> userVacancies = vacancyService.getAllByEmployer(employer.getEmail(), page - 1);

        model.addAttribute("vacancies", userVacancies);
        model.addAttribute("user", employer);
        return "/user/profile";
    }

    @GetMapping("account/profile")
    private String getProfile(
            Model model,
            Authentication auth,
            @RequestParam(name = "page", defaultValue = "1") int page) {

        UserDto user = userService.getByEmail(auth.getName());
        if ("applicant".equalsIgnoreCase(user.getAccountType())) {
            Page<ResumeDto> userResumes = resumeService.getAllByApplicant(user.getId(), page - 1);
            model.addAttribute("userResumes", userResumes);
        } else {
            Page<VacancyDto> userVacancies = vacancyService.getAllByEmployer(user.getEmail(), page - 1);
            model.addAttribute("vacancies", userVacancies);
        }
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @GetMapping("account/edit")
    public String edit(Model model) {
        model.addAttribute("userUpdateDto", new UserUpdateDto());
        return "/user/edit";
    }

    @PostMapping("account/edit")
    public String edit(Authentication auth,
                       @Valid UserUpdateDto userUpdateDto,
                       BindingResult bindingResult,
                       Model model) {
        if (!bindingResult.hasErrors()) {
            userService.update(auth, userUpdateDto, userService.getByEmail(auth.getName()).getId());
            return "redirect:/users";
        }
        model.addAttribute("userUpdateDto", userUpdateDto);
        return "/user/edit";
    }

    @GetMapping("login")
    public String login() {
        return "/user/login";
    }
}
