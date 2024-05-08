package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserLoginDto;
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
@RequestMapping("users")
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
            return "redirect:/users/login";
        }
        model.addAttribute("userCreationDto", dto);
        return "user/register";
    }

    @GetMapping
    public String getUser (Model model,
                           Authentication authentication,
                           @RequestParam (name="page", defaultValue = "1") int page) {
        if ("applicant".equalsIgnoreCase(userService.getByEmail(authentication.getName()).getAccountType())) {
            UserDto userDto = userService.getByEmail(authentication.getName());
            Page<ResumeDto> userResumes = resumeService.getAllByApplicant(userDto.getId(), page - 1);
            model.addAttribute("userResumes", userResumes);
        } else {
            Page<VacancyDto> userVacancies = vacancyService.getAllByEmployer(authentication, page - 1);
            model.addAttribute("vacancies", userVacancies);
        }
        model.addAttribute("page", page);
        return "/user/profile";
    }

    @GetMapping("profile/edit")
    public String edit(Model model) {
        model.addAttribute("userUpdateDto", new UserUpdateDto());
        return "/user/edit";
    }

    @PostMapping("profile/edit")
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

    @PostMapping("login")
    public String login(Authentication auth, @RequestBody UserLoginDto userLoginDto) {
        userService.login(auth, userLoginDto);

        return "redirect:/users";
    }
}
