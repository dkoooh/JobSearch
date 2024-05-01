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
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    private String name;
    @Lob
    private String surname;
    private Integer age;
    private String email;
    @Lob
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Lob
    private String avatar;
    @Column(name = "account_type")
    private String accountType;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Resume> resumes;
}
