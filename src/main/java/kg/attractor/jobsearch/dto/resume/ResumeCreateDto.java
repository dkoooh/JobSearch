package kg.attractor.jobsearch.dto.resume;

import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeCreateDto {
    private String applicantEmail;
    private String name;
    private Integer categoryId;
    private Double Salary;
    private Boolean isActive;
    private List<WorkExpInfoCreateDto> workExperienceInfo;
    private List<EduInfoCreateDto> educationInfo;
    private List<ContactInfoCreateDto> contacts;
}
