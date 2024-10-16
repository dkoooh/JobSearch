package kg.attractor.jobsearch.dto.workExpInfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExpInfoUpdateDto {
    private Integer id;
    @NotNull
    @Max(80) @Min(1)
    private Integer years;
    @NotBlank
    private String companyName;
    @NotBlank
    private String position;
    @NotBlank
    private String responsibilities;
}
