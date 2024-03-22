package kg.attractor.jobsearch.dto.vacancy;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VacancyUpdateDto {
    @NotNull
    private Integer id;
    @NotBlank
    @Email
    private String authorEmail;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Integer categoryId;
    private Double salary;
    @Min(0) @Max(80)
    private Integer expFrom;
    @Max(80) @Min(0)
    private Integer expTo;
    private Boolean isActive;
}
