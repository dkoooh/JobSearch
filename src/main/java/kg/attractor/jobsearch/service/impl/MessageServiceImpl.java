package kg.attractor.jobsearch.service.impl;
import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.exception.ForbiddenException;
import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.repository.MessageRepository;
import kg.attractor.jobsearch.repository.ResponseRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.ResponseService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    public List<MessageDto> getListMessagesGroups(Integer respondedApplicantId, String userEmail) {
        Integer employerId = responseService.getById(respondedApplicantId, userEmail).getVacancy().getAuthor().getId();
        Integer applicantId = responseService.getById(respondedApplicantId, userEmail).getResume().getApplicant().getId();
        Integer userId = userService.getByEmail(userEmail).getId();

        if ( !(userId.equals(employerId) || userId.equals(applicantId)) ) {
            throw new ForbiddenException("error.forbidden.chat");
        }

        List<Message> messages = messageRepository.findAllByResponseId(respondedApplicantId);
        return messages.stream().map(this::convertToDto).toList();
    }

    public void sendMessageGroup(Integer to, MessageDto dto, String userEmail) {
        System.out.println("String userEmail from Authentication auth: " + userEmail);

        Integer employerId = responseService.getById(dto.getResponseId(), userEmail).getVacancy().getAuthor().getId();
        Integer applicantId = responseService.getById(dto.getResponseId(), userEmail).getResume().getApplicant().getId();
        Integer userId = userService.getByEmail(userEmail).getId();

        if ( !(userId.equals(employerId) || userId.equals(applicantId)) ) {
            throw new ForbiddenException("error.forbidden.chat");
        }

        Message message = Message.builder()
                .content(dto.getContent())
                .sender(userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.user")))
                .response(responseRepository.findById(dto.getResponseId())
                        .orElseThrow(() -> new IllegalArgumentException("error.invalid.user")))
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
