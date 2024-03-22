package kg.attractor.jobsearch.dto.contactInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoCreateDto {
    private Integer typeId;
    private String contactValue;
}
