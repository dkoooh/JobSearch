package kg.attractor.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespondedApplicant {
    private Integer id;
    private Integer vacancyId;
    private Integer resumeId;
    private Boolean isConfirmed;
}
