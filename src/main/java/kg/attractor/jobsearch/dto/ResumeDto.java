package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeDto {
    private Integer id;
    private Integer applicantId;
    private String name;
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
}
