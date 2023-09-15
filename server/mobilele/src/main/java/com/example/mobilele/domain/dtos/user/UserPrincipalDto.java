package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.config.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipalDto {
    private String token;
    private String id;
    private String username;
    private List<String> authorities;

    public UserPrincipalDto (UserPrincipal entity){
        this.token = entity.getJwtToken();
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.authorities = entity.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }
}
