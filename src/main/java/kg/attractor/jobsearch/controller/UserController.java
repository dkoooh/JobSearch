package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.user.UserCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    @GetMapping("register")
    public String createUser () {
        return "user/register";
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser (Model model, UserCreationDto dto) {

    }
}
