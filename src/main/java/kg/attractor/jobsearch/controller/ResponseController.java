package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.resume.ResumeDto;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.Authentication;
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
    public String getResponses(Model model) {
        List<Map<String, Object>> responses = responseService.fetchAllGroups();
        model.addAttribute("responses", responses);
        return "response/response";
    }

    @GetMapping("chooseResume")
    public String chooseResume(@RequestParam Integer vacancyId, Model model, Authentication auth) {
        List<ResumeDto> resumes = resumeService.getResumesByApplicant(
                userService.getUserByEmail(auth.getName()).getId());

        model.addAttribute("resumes", resumes);
        model.addAttribute("vacancyId", vacancyId);

        return "response/resumes";
    }

    @GetMapping("respond")
    public String respond(@RequestParam Integer vacancyId, @RequestParam Integer resumeId) {
        responseService.create(vacancyId, resumeId);

        return "redirect:/responses";
    }
}
