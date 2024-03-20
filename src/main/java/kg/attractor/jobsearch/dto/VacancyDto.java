package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VacancyDto {
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private Double salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
    private String authorEmail;
    private Integer authorId;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
