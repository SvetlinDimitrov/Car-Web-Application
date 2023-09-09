package com.example.mobilele.config.security;

import com.example.mobilele.utils.constants.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthorizationFilter filter) throws Exception {

        return httpSecurity
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint(authenticationEntryPoint()))
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(HttpMethod.POST , "/car/api/user/login").anonymous()
                                .requestMatchers(HttpMethod.POST ,"/car/api/user/register").anonymous()
                                .requestMatchers(HttpMethod.GET ,"/car/api/brand").permitAll()
                                .requestMatchers(HttpMethod.PATCH , "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST , "/car/api/brand").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.GET ,"/car/api/model").permitAll()
                                .requestMatchers(HttpMethod.PATCH , "/car/api/model").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE , "/car/api/model").hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST , "/car/api/model").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        };
    }

}
