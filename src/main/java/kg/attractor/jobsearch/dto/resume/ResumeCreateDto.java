package kg.attractor.jobsearch.dto.resume;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoCreateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoCreateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoCreateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResumeCreateDto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", categoryId=").append(categoryId);
        sb.append(", Salary=").append(Salary);
        sb.append(", isActive=").append(isActive);
        sb.append(", workExperienceInfo=").append(workExperienceInfo);
        sb.append(", educationInfo=").append(educationInfo);
        sb.append(", contacts=").append(contacts);
        sb.append('}');
        return sb.toString();
    }
}
