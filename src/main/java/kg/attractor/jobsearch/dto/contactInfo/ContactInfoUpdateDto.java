package kg.attractor.jobsearch.dto.contactInfo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfoUpdateDto {
    @NotNull
    private Integer typeId;
    @NotNull
    private String contactValue;
}
