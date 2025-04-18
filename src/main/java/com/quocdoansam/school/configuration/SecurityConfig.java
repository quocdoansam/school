package com.quocdoansam.school.configuration;

import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import com.quocdoansam.school.enums.Role;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Value("${jwt.signerKey}")
        private String signerKey;

        @Bean
        SecurityFilterChain securityFilterChain(
                        HttpSecurity http,
                        JwtAuthenticationFilter jwtFilter) throws Exception {

                http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                                                .requestMatchers("/students/**", "/teachers/**")
                                                .hasRole(Role.ADMIN.name())

                                                .requestMatchers("/students/**").hasRole(Role.STUDENT.name())

                                                .requestMatchers("/teachers/**").hasRole(Role.TEACHER.name())

                                                .anyRequest().hasAnyRole(Role.ADMIN.name()))
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt
                                                                .decoder(jwtDecoder())
                                                                .jwtAuthenticationConverter(
                                                                                jwtAuthenticationConverter()))
                                                .authenticationEntryPoint(authenticationEntryPoint()));

                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        JwtDecoder jwtDecoder() {
                SecretKeySpec secretKey = new SecretKeySpec(signerKey.getBytes(), "HmacSHA512");
                return NimbusJwtDecoder
                                .withSecretKey(secretKey)
                                .macAlgorithm(MacAlgorithm.HS512)
                                .build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
                converter.setAuthorityPrefix("ROLE_");
                converter.setAuthoritiesClaimName("scope");

                JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
                jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
                return jwtConverter;
        }

        @Bean
        AuthenticationEntryPoint authenticationEntryPoint() {
                return (request, response, authException) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                        response.getWriter().write("{\"error\": \"Unauthorized: " + authException.getMessage() + "\"}");
                };
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowCredentials(true);
                cors.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:*"));
                cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                cors.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                cors.setExposedHeaders(List.of("Authorization"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", cors);
                return source;
        }
}
