package kg.attractor.jobsearch.dto.resume;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeCreateDto {
    @NotBlank
    private String name;
    @NotNull
    private Integer categoryId;
    private Double Salary;
    private Boolean isActive;
    @Valid
    private List<WorkExpInfoCreateDto> workExperienceInfo;
    @Valid
    private List<EduInfoCreateDto> educationInfo;
    @Valid @NotNull
    private List<ContactInfoCreateDto> contacts;
}
