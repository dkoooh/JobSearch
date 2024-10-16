package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "users")
public class User implements UserDetails {
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
    private String resetPasswordToken;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Resume> resumes;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Vacancy> vacancies;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sender")
    private List<Message> messages;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(accountType));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
