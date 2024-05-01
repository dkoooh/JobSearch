package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    private String name;
    private CategoryDto parent;
//    TODO PARENT-iD
}
