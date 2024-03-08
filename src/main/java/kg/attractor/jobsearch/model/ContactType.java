package kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactType {
    private Integer id;
    private String type;
}
