package kg.attractor.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .loginProcessingUrl("/login")
                            .defaultSuccessUrl("/account/profile")
                            .permitAll()
                )
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers("/applicant/{email}", "/resumes", "/resumes/categories",
                                                "/vacancies/create", "/vacancies/*/edit").hasAuthority("EMPLOYER")
                                        .requestMatchers("/", "/vacancies", "/vacancies/*", "/register", "employer/*").permitAll()
                                        .requestMatchers("resumes/create", "resumes/{id}/edit").hasAuthority("APPLICANT")
                                        .requestMatchers("api/resumes/applicant").hasAuthority("APPLICANT")
                                        .requestMatchers(HttpMethod.POST, "api/users").permitAll()
                                        .requestMatchers(HttpMethod.GET, "api/vacancies", "api/vacancies/*").permitAll()
                                        .requestMatchers(HttpMethod.GET, "api/resumes", "api/resumes/**").hasAuthority("EMPLOYER")
                                        .requestMatchers(HttpMethod.GET, "api/responses/*").hasAuthority("APPLICANT")
                                        .requestMatchers("api/vacancies", "api/vacancies/*", "api/users/applicants/*", "api/users/vacancies/*").hasAuthority("EMPLOYER")
                                        .requestMatchers("api/resumes", "api/resumes/*", "api/users/employers/*").hasAuthority("APPLICANT")
                                        .requestMatchers("data/**").permitAll()
                                        .anyRequest().authenticated()
                );
        return http.build();

    }
}
