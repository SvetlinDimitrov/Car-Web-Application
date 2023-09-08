package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.config.security.UserPrincipal;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class UserPrincipalDto {
    private String jwtToken;
    private String id;
    private String username;
    private List<String> authorities;

    public UserPrincipalDto (UserPrincipal entity){
        this.jwtToken = entity.getJwtToken();
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.authorities = entity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
}
