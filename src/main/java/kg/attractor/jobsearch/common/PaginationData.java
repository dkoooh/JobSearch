package kg.attractor.jobsearch.common;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationData {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer pagesAvailable;
    private Sort sortDirection;
    private String sortField;
}
