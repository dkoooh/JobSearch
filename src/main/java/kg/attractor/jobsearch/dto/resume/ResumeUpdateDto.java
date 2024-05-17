package kg.attractor.jobsearch.dto.resume;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kg.attractor.jobsearch.dto.contactInfo.ContactInfoUpdateDto;
import kg.attractor.jobsearch.dto.workExpInfo.WorkExpInfoUpdateDto;
import kg.attractor.jobsearch.dto.educationInfo.EduInfoUpdateDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeUpdateDto {
    @NotNull
    private Integer id;
    @NotBlank
    private String name;
    @NotNull
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    @Valid
    private List<WorkExpInfoUpdateDto> workExperienceInfo = new ArrayList<>();
    @Valid
    private List<EduInfoUpdateDto> educationInfo = new ArrayList<>();
    @Valid @NotNull
    private List<ContactInfoUpdateDto> contacts;
}
