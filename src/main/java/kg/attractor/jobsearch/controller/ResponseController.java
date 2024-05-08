package kg.attractor.jobsearch.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final ResumeService resumeService;
    private final UserService userService;

    @GetMapping
    public String getResponses(Model model, Authentication auth) {
        List<Map<String, Object>> responses = responseService.fetchAllGroups();
        model.addAttribute("responses", responses);
        if (auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("APPLICANT")) {
            return "response/applicant/responses";
        } else {
            return "response/employer/responses";
        }
    }

    @GetMapping("chooseResume")
    public String chooseResume(@RequestParam Integer vacancyId, Model model, Authentication auth) {
        List<ResumeDto> resumes = resumeService.getAllByApplicant(
                userService.getByEmail(auth.getName()).getId());

        model.addAttribute("resumes", resumes);
        model.addAttribute("vacancyId", vacancyId);

        return "response/applicant/resumes";
    }

    @PostMapping("respond")
    public String respond(@NotNull Integer vacancyId, @NotNull Integer resumeId) {
        responseService.create(vacancyId, resumeId);
        return "redirect:/responses";
    }
}
