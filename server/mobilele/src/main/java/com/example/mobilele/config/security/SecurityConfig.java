package com.example.mobilele.config.security;

import com.example.mobilele.utils.constants.Role;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
                                .requestMatchers(HttpMethod.GET ,"/car/api/offer").permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
