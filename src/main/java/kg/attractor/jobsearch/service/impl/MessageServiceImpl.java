package kg.attractor.jobsearch.service.impl;
import kg.attractor.jobsearch.dao.MessageDao;
import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.exception.NoAccessException;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
//    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final ResponseService responseService;
    private final MessageDao messageDao;

//    public void sendMessage(String to, MessageDto messageDto) {
//        template.update("insert into MESSAGES(/*  */) values ()");
//        simpMessagingTemplate.convertAndSend("/topic/messages/" + to, messageDto);
//    }
//
//    public List<Map<String, Object>> getListMessage(@PathVariable("from") Integer fromm, @PathVariable("to") Integer to) {
//        return template.queryForList("select * from MESSAGES where (/*message_from=? message_to=?*/) or " +
//                "(/*msg_to=? msg_from=?*/) order by /*created_date_time*/ asc", from, to, from, to);
//    }

    public List<Map<String, Object>> getListMessagesGroups(Integer respondedApplicantId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userService.getUserByEmail(userEmail).getId().equals(responseService.getById(respondedApplicantId).getVacancyId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseService.getById(respondedApplicantId).getResumeId())) {
            throw new NoAccessException("Cannot access the chat.ftlh you're not a member of");
        }

        return messageDao.getListMessagesGroups(respondedApplicantId);
    }

    public void sendMessageGroup(Integer to, MessageDto msg) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userService.getUserByEmail(userEmail).getId().equals(responseService.getById(to).getVacancyId()) &&
                !userService.getUserByEmail(userEmail).getId().equals(responseService.getById(to).getResumeId())) {
            throw new NoAccessException("Cannot access the chat.ftlh you're not a member of");
        }
        Integer userId = userService.getUserByEmail(userEmail).getId();

        messageDao.sendMessageGroup(to, msg, userId);

//        msg.setRespondedApplicantId(to);
//        simpMessagingTemplate.convertAndSend("/topic/messages/group/" + to, msg);
    }
}
