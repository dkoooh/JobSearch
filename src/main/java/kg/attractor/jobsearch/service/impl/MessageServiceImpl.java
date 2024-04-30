package kg.attractor.jobsearch.service.impl;
import kg.attractor.jobsearch.dao.MessageDao;
import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.exception.NoAccessException;
import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final ResponseService responseService;
    private final MessageDao messageDao;

    public List<MessageDto> getListMessagesGroups(Integer respondedApplicantId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userService.getUserByEmail(userEmail).getId().equals(responseService.getById(respondedApplicantId).getVacancy().getAuthor().getId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseService.getById(respondedApplicantId).getResume().getApplicant().getId())) {
            throw new NoAccessException("Cannot access the chat you're not a member of");
        }


        List<Message> msgs = messageDao.getListMessagesGroups(respondedApplicantId);
        return msgs.stream().map(this::convertToDto).toList();
    }

    public void sendMessageGroup(Integer to, MessageDto msg, String userEmail) {
        System.out.println("String userEmail from Authentication auth: " + userEmail);

        if (!userService.getUserByEmail(userEmail).getId().equals(responseService.getById(to, userEmail).getVacancy().getAuthor().getId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseService.getById(to, userEmail).getResume().getApplicant().getId())) {
            throw new NoAccessException("Cannot access the chat you're not a member of");
        }
        Integer userId = userService.getUserByEmail(userEmail).getId();

        messageDao.sendMessageGroup(to, msg, userId);
        msg.setSenderId(userId);

        String destination = "/response/" + to + "/queue/messages";
        System.out.println(destination);
//        msg.setRespondedApplicantId(to);
        simpMessagingTemplate.convertAndSend(destination, msg);
    }

    private MessageDto convertToDto (Message msg) {
        return MessageDto.builder()
                .content(msg.getContent())
                .responseId(msg.getRespondedApplicantId())
                .senderId(msg.getSenderId())
                .timeStamp(msg.getTimeStamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }


}
