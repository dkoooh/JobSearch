package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Lob
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Double salary;
    @Column(name = "exp_from")
    private Integer expFrom;
    @Column(name = "exp_to")
    private Integer expTo;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vacancy")
    private List<RespondedApplicant> responses;
}
