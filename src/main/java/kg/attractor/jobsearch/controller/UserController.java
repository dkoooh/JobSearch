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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    @GetMapping("register")
    public String create() {
        return "user/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String create(@Valid UserCreationDto dto) {
        userService.create(dto);
        return "redirect:/users";
    }

    @GetMapping
    public String getUser (Model model, Authentication authentication) {
        UserDto user = userService.getUserByEmail(authentication.getName());

        if ("applicant".equalsIgnoreCase(userService.getUserByEmail(authentication.getName()).getAccountType())) {
            List<ResumeDto> userResumes = resumeService.getResumesByApplicant(user.getId());
            model.addAttribute("userResumes", userResumes);
        } else {
            List<VacancyDto> userVacancies = vacancyService.getVacanciesByEmployer(authentication);
            model.addAttribute("vacancies", userVacancies);
        }
        model.addAttribute("user", user);

        return "/user/profile";
    }

    @GetMapping("profile/edit")
    public String edit(Model model, Authentication authentication) {
        UserDto user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "/user/edit";
    }

    @PostMapping("profile/edit")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String edit(@Valid UserUpdateDto dto, Authentication auth) {
//        if (result.hasErrors()) {
//            System.err.println("ERROR FILE");
//            System.err.println(result.getModel());
//            return "redirect:/";
//        }
//        MultipartFile file = dto.getAvatar();
//        System.out.println("file = " + file);
        userService.update(auth, dto, userService.getUserByEmail(auth.getName()).getId());
        return "redirect:/users";
    }

    @GetMapping("login")
    public String login() {
        return "/user/login";
    }

//    @PostMapping("login")
//    public String login(Authentication auth, @Valid UserLoginDto userLoginDto) {
//        userService.login(auth, userLoginDto);
//
//        return "redirect:/users";
//    }
}
