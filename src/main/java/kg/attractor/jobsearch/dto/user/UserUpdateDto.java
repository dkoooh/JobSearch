package kg.attractor.jobsearch.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    @NotBlank (message = "Имя не может быть пустым")
    private String name;
    private String surname;
    @Min(16) @Max(100)
    private Integer age;
    @NotBlank (message = "Пароль не может быть пустым")
    private String password;
    @NotBlank (message = "Номер телефона не может быть пустым")
    private String phoneNumber;
    private MultipartFile avatar;
}
