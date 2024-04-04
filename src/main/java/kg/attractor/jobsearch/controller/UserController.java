package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping("register")
    public String createUser () {
        return "user/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser (UserCreationDto dto) {
        userService.create(dto);
        return "redirect:/";
    }

    @GetMapping
    public String getUser (Model model, Authentication authentication) {
        UserDto user = userService.getUserByEmail(authentication.getName());
        List<ResumeDto> userResumes = resumeService.getResumesByApplicant(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("userResumes", userResumes);
        return "/user/profile";
    }

    @GetMapping("profile/edit")
    public String editUser (Model model, Authentication authentication) {
        UserDto user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "/user/edit";
    }

    @PostMapping("profile/edit")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String editUser (UserUpdateDto dto, Authentication auth) {
        userService.update(auth, dto, userService.getUserByEmail(auth.getName()).getId());
        return "redirect:/";
    }
}
