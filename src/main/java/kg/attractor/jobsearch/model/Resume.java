package kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Resume {
    private Integer id;
    private Integer applicantId;
    private String name;
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
