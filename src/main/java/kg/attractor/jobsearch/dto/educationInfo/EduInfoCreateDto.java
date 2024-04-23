package kg.attractor.jobsearch.dto.educationInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EduInfoCreateDto {
    @NotBlank
    private String institution;
    @NotBlank
    private String program;

    @NotNull
    @Past
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotBlank
    private String degree;
}
