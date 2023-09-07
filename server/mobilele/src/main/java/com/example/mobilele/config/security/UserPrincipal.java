package com.example.mobilele.config.security;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter

public class UserPrincipal extends User {
    private String jwtToken;
    private String id;
    private String username;

    public UserPrincipal(com.example.mobilele.domain.entity.User entity) {
        super(entity.getUsername(),
                entity.getPassword(),
                entity.getUserRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name())).toList());
    }
}
