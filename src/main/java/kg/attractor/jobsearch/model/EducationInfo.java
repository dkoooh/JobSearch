package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "education_info")
public class EducationInfo {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
    @Lob
    private String institution;
    @Lob
    private String program;
    @Column (name = "start_date")
    private LocalDate startDate;
    @Column (name = "end_date")
    private LocalDate endDate;
    @Lob
    private String degree;
}
