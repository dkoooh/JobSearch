package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.ContactInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ContactInfoDao {
    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate namedTemplate;

//    public void create(ContactInfo info) {
//        String sql = """
//                insert into CONTACTS_INFO (type_id, RESUME_ID, CONTACT_VALUE)
//                 values ( ?, ?, ?);
//                """;
//
//        SqlParameterSource dataSource = new MapSqlParameterSource()
//                .addValue("type_id", info.getType())
//                .addValue("resume_id", info.getResumeId())
//                .addValue("contact_value", info.getContactValue());
//
//        template.update(sql, info.getType(), info.getResumeId(), info.getContactValue());
//    }

//    public List<ContactInfo> getByResumeId (int resumeId) {
//        String sql = """
//                select * from CONTACTS_INFO
//                where RESUME_ID = ?;
//                """;
//
//        return template.query(sql, new BeanPropertyRowMapper<>(ContactInfo.class), resumeId);
//    }

//    public void update(ContactInfo info) {
//        String sql = """
//                update CONTACTS_INFO
//                set CONTACT_VALUE = :contact_value
//                where RESUME_ID = :resume_id
//                and TYPE_ID = :type_id
//                """;
//
//        SqlParameterSource dataSource = new MapSqlParameterSource()
//                .addValue("type_id", info.getType())
//                .addValue("resume_id", info.getResumeId())
//                .addValue("contact_value", info.getContactValue());
//
//        namedTemplate.update(sql, dataSource);
//    }

//    public Boolean isContactTypeExists (int typeId, int resumeId) {
//        String sql = """
//                select * from contacts_info
//                where type_id = ?
//                and resume_id = ?
//                """;
//
//        return !template.query(sql, new BeanPropertyRowMapper<>(ContactInfo.class), typeId, resumeId).isEmpty();
//    }

}
