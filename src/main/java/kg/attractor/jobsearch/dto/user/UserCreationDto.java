package kg.attractor.jobsearch.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UserCreationDto {
    @NotBlank
    private String name;
    private String surname;
    @Min(16)
    @Max(100)
    private Integer age;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
//    private MultipartFile avatar;
    @NotBlank
    private String accountType;
}
