package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "contact_types")
public class ContactType {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private String type;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
    private List<ContactInfo> contactInfos;
}
