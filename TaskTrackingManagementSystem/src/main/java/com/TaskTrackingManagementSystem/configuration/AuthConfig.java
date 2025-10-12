package com.TaskTrackingManagementSystem.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class AuthConfig {
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
}
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/registration", "/register").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(withDefaults());
    return http.build();
}
}