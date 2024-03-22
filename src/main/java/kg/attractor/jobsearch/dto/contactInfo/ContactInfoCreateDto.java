package kg.attractor.jobsearch.dto.contactInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoCreateDto {
    @NotNull
    private Integer typeId;
    @NotBlank
    private String contactValue;
}
