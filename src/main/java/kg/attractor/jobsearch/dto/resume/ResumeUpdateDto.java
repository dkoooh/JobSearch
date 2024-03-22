package kg.attractor.jobsearch.dto.resume;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResumeUpdateDto {
    @NotNull
    private Integer id;
    @Email @NotBlank
    private String applicantEmail;
    @NotBlank
    private String name;
    @NotNull
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    @Valid
    private List<WorkExpInfoUpdateDto> workExperienceInfo;
    @Valid
    private List<EduInfoUpdateDto> educationInfo;
    @Valid @NotNull
    private List<ContactInfoUpdateDto> contacts;
}
