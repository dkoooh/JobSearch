package kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespondedApplicant {
    private Integer id;
    private Integer vacancyId;
    private Integer resumeId;
    private Boolean isConfirmed;
}
