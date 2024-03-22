package kg.attractor.jobsearch.dto.resume;

import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeUpdateDto {
    private Integer id;
    private String applicantEmail;
    private String name;
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    private List<WorkExpInfoUpdateDto> workExperienceInfo;
    private List<EduInfoUpdateDto> educationInfo;
    private List<ContactInfoUpdateDto> contacts;
}
