package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("api/chats")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {
    private final MessageService messageService;
    private final ResponseService responseService;

    @MessageMapping("/{groupId}")
    public void sendMessageToGroup (@DestinationVariable Integer groupId,
                                    @Payload MessageDto messageDto,
                                    Authentication auth) {
        messageService.sendMessageGroup(groupId, messageDto, auth.getName());
    }

    @GetMapping("/{groupId}")
    public List<MessageDto> getListMessageGroupChat(@PathVariable Integer groupId, Authentication auth) {
        return messageService.getListMessagesGroups(groupId, auth.getName());
    }

    @GetMapping("/")
    public List<Map<String, Object>> fetchAllGroups() {
        return responseService.fetchAllGroups();
    }
}
