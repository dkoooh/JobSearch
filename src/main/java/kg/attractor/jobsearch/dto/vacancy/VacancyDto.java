package kg.attractor.jobsearch.dto.vacancy;

import kg.attractor.jobsearch.dto.user.UserDto;
import kg.attractor.jobsearch.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VacancyDto {
    private Integer id;
    private String name;
    private String description;
    private String category;
    private Double salary;
    private Integer expFrom;
    private Integer expTo;
    private Boolean isActive;
    private UserDto author;
    private String createdDate;
    private String updateTime;
}
