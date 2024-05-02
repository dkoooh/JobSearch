package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "responded_applicants")
public class RespondedApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;
    @ManyToOne
    @JoinColumn(name = "resume_Id")
    private Resume resume;
    @Column(name = "confirmation")
    private Boolean isConfirmed;
}
