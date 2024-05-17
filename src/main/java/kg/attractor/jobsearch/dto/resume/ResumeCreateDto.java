package kg.attractor.jobsearch.dto.resume;

import jakarta.validation.Valid;
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
    @NotBlank (message = "Название не может быть пустым")
    private String name;
    @NotNull (message = "Необходимо выбрать категорию резюме")
    private Integer categoryId;
    private Double salary;
    private Boolean isActive;
    @Valid
    private List<WorkExpInfoCreateDto> workExperienceInfo;
    @Valid
    private List<EduInfoCreateDto> educationInfo;
    @Valid @NotNull
    private List<ContactInfoCreateDto> contacts;
}
