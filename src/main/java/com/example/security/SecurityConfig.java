package com.example.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtTokenFilter JwtTokenFilter;

    @Autowired
    public SecurityConfig(JwtTokenFilter JwtTokenFilter) {
        this.JwtTokenFilter = JwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/users/register").permitAll()
//                .requestMatchers("/api/users/login").permitAll()
//                .requestMatchers(HttpMethod.GET,"/api/amenities").permitAll()
                        .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated())
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(JwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e -> e.authenticationEntryPoint((request, response, authException)
                                -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED))

        );
        return http.build();
    }
}
