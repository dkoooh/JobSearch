package kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Resume {
    private Integer id;
    private Integer applicantId;
    private String name;
    private Integer category_id;
    private Double salary;
    private Boolean isActive;
    private LocalDate createdDate;
    private LocalDate updateTime;
}
