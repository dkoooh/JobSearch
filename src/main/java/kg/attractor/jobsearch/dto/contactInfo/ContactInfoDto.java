package kg.attractor.jobsearch.dto.contactInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoDto {
    private Integer typeId;
    private String value;
}
