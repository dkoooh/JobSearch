package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTACT_TYPES")
public class ContactType {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<ContactInfo> contactInfoList;
}