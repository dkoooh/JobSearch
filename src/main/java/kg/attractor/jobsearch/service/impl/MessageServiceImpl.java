package kg.attractor.jobsearch.service.impl;
import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.exception.NoAccessException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.repository.MessageRepository;
import kg.attractor.jobsearch.repository.ResponseRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final MessageRepository messageRepository;
    private final ResponseRepository responseRepository;

    public List<MessageDto> getListMessagesGroups(Integer respondedApplicantId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!userService.getByEmail(userEmail).getId()
                .equals(responseService.getById(respondedApplicantId).getVacancy().getAuthor().getId()) &&
                !userService.getByEmail(userEmail).getId()
                        .equals(responseService.getById(respondedApplicantId).getResume().getApplicant().getId())) {
            throw new NoAccessException("Cannot access the chat you're not a member of");
        }

        List<Message> messages = messageRepository.findAllByResponseId(respondedApplicantId);
        return messages.stream().map(this::convertToDto).toList();
    }

    public void sendMessageGroup(Integer to, MessageDto dto, String userEmail) {
        System.out.println("String userEmail from Authentication auth: " + userEmail);
        if (!userService.getByEmail(userEmail).getId().equals(responseService.getById(to, userEmail).getVacancy().getAuthor().getId()) &&
                !userService.getByEmail(userEmail).getId().equals(responseService.getById(to, userEmail).getResume().getApplicant().getId())) {
            throw new NoAccessException("Cannot access the chat you're not a member of");
        }
        Integer userId = userService.getByEmail(userEmail).getId();

        Message message = Message.builder()
                .content(dto.getContent())
                .sender(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Not found user with ID: " + dto.getSenderId())))
                .response(responseRepository.findById(dto.getResponseId())
                        .orElseThrow(() -> new NotFoundException("Not found response with ID: " + dto.getResponseId())))
                .timestamp(LocalDateTime.now())
                .build();

        messageRepository.save(message);

        String destination = "/response/" + to + "/queue/messages";
        simpMessagingTemplate.convertAndSend(destination, dto);
    }

    private MessageDto convertToDto (Message msg) {
        return MessageDto.builder()
                .content(msg.getContent())
                .responseId(msg.getResponse().getId())
                .senderId(msg.getSender().getId())
                .timeStamp(msg.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }


}
