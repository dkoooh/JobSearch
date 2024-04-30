package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
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


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/users/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/users")
                            .permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(HttpMethod.GET, "/", "/vacancies", "vacancies/*").permitAll()
                                        .requestMatchers(HttpMethod.GET, "users/register").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/vacancies", "/vacancies/*").hasAuthority("EMPLOYER")
                                        .requestMatchers(HttpMethod.POST, "resumes", "resumes/*").hasAuthority("APPLICANT")
                                        .requestMatchers(HttpMethod.POST, "api/users", "users/register").permitAll()
                                        .requestMatchers(HttpMethod.GET, "api/vacancies", "api/vacancies/*").permitAll()
                                        .requestMatchers(HttpMethod.GET, "api/resumes", "api/resumes/**").hasAuthority("EMPLOYER")
                                        .requestMatchers(HttpMethod.GET, "api/responses/*").hasAuthority("APPLICANT")
                                        .requestMatchers("api/vacancies", "api/vacancies/*", "api/users/applicants/*", "api/users/vacancies/*").hasAuthority("EMPLOYER")
                                        .requestMatchers("api/resumes", "api/resumes/*", "api/users/employers/*").hasAuthority("APPLICANT")
                                        .requestMatchers("data/**").permitAll()
                                        .anyRequest().authenticated()
//                                    .anyRequest().permitAll()
                );
        return http.build();

    }
}
