package kg.attractor.jobsearch.dto.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserUpdateDto {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String password;
    private String phoneNumber;
    private MultipartFile avatar;
}
