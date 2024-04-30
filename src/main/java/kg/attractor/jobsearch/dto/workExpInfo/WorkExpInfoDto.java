package kg.attractor.jobsearch.dto.workExpInfo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkExpInfoDto {
    private Integer id;
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
