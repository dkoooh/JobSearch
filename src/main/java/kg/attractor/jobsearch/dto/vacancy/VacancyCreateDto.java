package kg.attractor.jobsearch.dto.vacancy;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyCreateDto {
    @NotBlank (message = "Название не может быть пустым")
    private String name;
    private String description;
    @NotNull (message = "Необходимо выбрать категорию вакансии")
    private Integer categoryId;
    private Double salary;
    @Max(value = 70, message = "Некорректное значение")
    @Min(value = 0, message = "Некорректное значение")
    private Integer expFrom;
    @Max(value = 70, message = "Некорректное значение")
    @Min(value = 0, message = "Некорректное значение")
    private Integer expTo;
    private Boolean isActive;
};
