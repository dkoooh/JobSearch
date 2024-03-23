package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final DataSource dataSource;

    @Bean
    public PasswordEncoder encoder () {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        String userQuery = """
                select email, password, enabled from users
                where email = ?;
                """;

        String authorityQuery = """
                select u.email, a.authority from USER_AUTHORITY ua, USERS u, AUTHORITIES a
                where ua.USER_ID = u.ID
                and ua.AUTHORITY_ID = a.ID
                and EMAIL = ?;
                """;

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(authorityQuery)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

}
