package kg.attractor.jobsearch.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactInfo {
    private Integer id;
    private Integer typeId;
    private Integer resumeId;
    private String value;
}
