package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("responses")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @GetMapping
    public String get(Model model) {
        List<Map<String, Object>> responses = responseService.fetchAllGroups();
        model.addAttribute("responses", responses);
        return "response/response";
    }
}
