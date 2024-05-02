package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.ContactType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactTypeDao {
    private final JdbcTemplate template;

//    public Boolean isTypeExists(int typeId) {
//        String sql = """
//                select * from CONTACT_TYPES
//                where ID = ?;
//                """;
//
//        return !template.query(sql, new BeanPropertyRowMapper<>(ContactType.class), typeId).isEmpty();
//    }
//
//    public Optional<ContactType> getById(Integer typeId) {
//        String sql = """
//                select * from CONTACT_TYPES
//                where ID = ?;
//                """;
//
//        return Optional.ofNullable(
//                DataAccessUtils.singleResult(
//                        template.query(sql, new BeanPropertyRowMapper<>(ContactType.class), typeId)
//                )
//        );
//    }
}
