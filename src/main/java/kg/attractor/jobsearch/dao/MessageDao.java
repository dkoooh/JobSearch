package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dto.message.MessageDto;
import kg.attractor.jobsearch.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageDao {
    private final JdbcTemplate template;

//    public List<Message> getListMessagesGroups(Integer respondedApplicantId) {
//        String sql = """
//                select * from MESSAGES
//                where RESPONDED_APPLICANT_ID = ?
//                """;
//
//        return template.query(sql, new BeanPropertyRowMapper<>(Message.class), respondedApplicantId);
//    }
//
//    public void sendMessageGroup(Integer to, MessageDto msg, Integer userId) {
//        String sql = """
//                INSERT INTO MESSAGES(SENDER_ID,RESPONDED_APPLICANT_ID, CONTENT, TIMESTAMP)
//                values (?,?,?, current_timestamp())
//                """;
//
//        template.update(sql, userId, to, msg.getContent());
//    }
}
