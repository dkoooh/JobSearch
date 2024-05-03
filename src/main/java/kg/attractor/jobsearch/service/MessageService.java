package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.message.MessageDto;

import java.util.List;

public interface MessageService {
    List<MessageDto> getListMessagesGroups(Integer respondedApplicantId, String userEmail);

    void sendMessageGroup(Integer to, MessageDto msg, String userEmail);


}
