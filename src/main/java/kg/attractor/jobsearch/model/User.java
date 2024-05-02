package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

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
    private Boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Resume> resumes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Vacancy> vacancies;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Message> messages;

    @ManyToMany (cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_authority",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_id")}
    )
    Set<Authority> authorities;

//    todo не создаются authority
}
