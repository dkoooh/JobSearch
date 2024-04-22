package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.message.MessageDto;

import java.util.List;
import java.util.Map;

public interface MessageService {
    List<Map<String, Object>> getListMessagesGroups(Integer respondedApplicantId);

    public void sendMessageGroup(Integer to, MessageDto msg);


}
