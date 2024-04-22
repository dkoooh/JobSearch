package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {
    private final MessageService messageService;
    private final ResponseService responseService;

//    @MessageMapping("/chat.ftlh/to")
//    public void sendMessagePersonal(@DestinationVariable String to, MessageDto messageDto) {
//        messageService.sendMessage(to, messageDto);
//    }
//
//    @GetMapping("listmessage/{from}/{to}")
//    public List<Map<String, Object>> getListMessageChat(@PathVariable("from") Integer from, @PathVariable("to") Integer to) {
//        return messageService.getListMessage(from, to);
//    }

    @PostMapping("/chat/group/{groupId}")
    public void sendMessageToGroup (@PathVariable(name = "groupId") Integer to, @RequestBody MessageDto messageDto) {
        messageService.sendMessageGroup(to, messageDto);
    }

    @GetMapping("listmessage/group/{groupId}")
    public List<Map<String, Object>> getListMessageGroupChat(@PathVariable Integer groupId) {
        return messageService.getListMessagesGroups(groupId);
    }

//    @GetMapping("/fetchAllUsers/{myId}")
//    public List<Map<String, Object>> fetchAll(@PathVariable("myId") String myId) {
//        return userAndGroupService.fetchAll(myId);
//    }

    @GetMapping("/fetchAllGroups")
    public List<Map<String, Object>> fetchAllGroups() {
        return responseService.fetchAllGroups();
    }
}
