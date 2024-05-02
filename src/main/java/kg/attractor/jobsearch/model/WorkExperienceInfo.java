package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_experience_info")
public class WorkExperienceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private Integer years;
    @Lob
    @Column(name = "company_name")
    private String companyName;
    @Lob
    private String position;
    @Lob
    private String responsibilities;
}
