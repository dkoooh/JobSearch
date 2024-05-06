package kg.attractor.jobsearch.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String email;
    @NotBlank
    private String name;
    private String surname;
    @Min(16) @Max(100)
    private Integer age;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    private MultipartFile avatar;
}
