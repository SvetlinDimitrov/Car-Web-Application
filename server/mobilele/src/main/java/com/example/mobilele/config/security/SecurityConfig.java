package com.example.mobilele.config.security;

import com.example.mobilele.utils.constants.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthorizationFilter filter) throws Exception {

        return httpSecurity
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/v3/api-docs",
                                        "/v2/api-docs",
                                        "/webjars/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui").permitAll()
                                .requestMatchers(HttpMethod.POST, "/car/api/user/login").anonymous()
                                .requestMatchers(HttpMethod.POST, "/car/api/user/register").anonymous()
                                .requestMatchers(HttpMethod.GET, "/car/api/brand").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/car/api/model").permitAll()
                                .requestMatchers(HttpMethod.PATCH, "/car/api/model").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, "/car/api/model").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, "/car/api/model").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/car/api/offer").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
