package kg.attractor.jobsearch.dto.contactInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfoUpdateDto {
    private Integer typeId;
    private String contactValue;
}
