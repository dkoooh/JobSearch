package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private Integer id;
    private Integer resumeId;
    private Integer vacancyId;
    private Boolean isConfirmed;
}
