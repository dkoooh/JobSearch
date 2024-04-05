package kg.attractor.jobsearch.dto.resume;

import io.swagger.v3.oas.models.info.Contact;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResumeDto {
    private Integer id;
    private Integer applicantId;
    private String name;
    private String category;
    private Double salary;
    private Boolean isActive;
    private List<EduInfoDto> educationInfo;
    private List<WorkExpInfoDto> workExperienceInfo;
    private List<ContactInfoDto> contactInfos;
    private LocalDateTime createdDate;
    private LocalDateTime updateTime;
}
