package kg.attractor.jobsearch.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationDto {
    @NotBlank
            (message = "Имя не может быть пустым")
    private String name;
    private String surname;
    @Min(16)
    @Max(100)
    private Integer age;
    @Email
    @NotBlank
            (message = "Email не может быть пустым")
    private String email;

    @NotBlank
            (message = "Пароль не может быть пустым")
    private String password;
    @NotBlank
            (message = "Номер телефона не может быть пустым")
    private String phoneNumber;
    @NotBlank
    private String accountType;
}
