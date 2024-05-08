package kg.attractor.jobsearch.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String accountType;
    private String password;
}
