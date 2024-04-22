package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dto.message.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageDao {
    private final JdbcTemplate template;

    public List<Map<String, Object>> getListMessagesGroups(Integer respondedApplicantId) {
        String sql = """
                select * from MESSAGES
                where RESPONDED_APPLICANT_ID = ?
                """;

        return template.queryForList(sql, respondedApplicantId);
    }

    public void sendMessageGroup(Integer to, MessageDto msg, Integer userId) {
        String sql = """
                INSERT INTO MESSAGES(SENDER_ID,RESPONDED_APPLICANT_ID, CONTENT, TIMESTAMP)
                values (?,?,?, current_timestamp())
                """;

        template.update(sql, userId, to, msg.getContent());
    }
}
