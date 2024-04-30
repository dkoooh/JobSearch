package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResponseDto;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ResponseService responseService;

    @GetMapping("/chats/{id}")
    public String getChat (@PathVariable(name="id") Integer responseId, Model model) {
        ResponseDto response = responseService.getById(responseId);
        model.addAttribute("response", response);
        return "chat/chat";
    }
}
