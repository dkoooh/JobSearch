package kg.attractor.jobsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class AppConfig {
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver();
    }
}
