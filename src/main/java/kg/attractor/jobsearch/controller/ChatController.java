package kg.attractor.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    @GetMapping("/chats/{id}")
    public String getChat (@PathVariable(name="id") Integer responseId) {
        return "chat/chat";
    }
}
