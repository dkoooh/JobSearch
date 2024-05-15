package kg.attractor.jobsearch.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.dto.user.UserCreationDto;
import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.dto.user.UserUpdateDto;
import kg.attractor.jobsearch.dto.vacancy.VacancyDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.messaging.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

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
    public String create(@Valid UserCreationDto dto,
                         BindingResult bindingResult,
                         Model model,
                         HttpServletRequest request) {
        if (!bindingResult.hasErrors()) {
            userService.create(dto);

            try {
                request.login(dto.getEmail(), dto.getPassword());
                return "redirect:/";
            } catch (ServletException e) {
                return "redirect:/login";
            }
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

    @GetMapping("account/forgot_password")
    public String showForgotPassword () {
        return "/user/forgot_password_form";
    }

    @PostMapping("account/forgot_password")
    public String processForgotPassword (HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswdLink(request);
            model.addAttribute("message", "We have sent a reset password link to your email");
        } catch (UsernameNotFoundException | UnsupportedEncodingException | jakarta.mail.MessagingException e) {
            model.addAttribute("error", e.getMessage());
        } catch (MessagingException ex) {
            model.addAttribute("error", "Error while sending email");
        }

        return "user/forgot_password_form";
    }

    @GetMapping("account/reset_password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        try {
            userService.getByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Invalid token");
        }
        return "user/reset_password_form";
    }

    @PostMapping("account/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            User user = userService.getByResetPasswordToken(token);
            userService.updatePassword(user, password);
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Invalid Token");
        }
        return "message";
    }

    @GetMapping("login")
    public String login() {
        return "/user/login";
    }
}
