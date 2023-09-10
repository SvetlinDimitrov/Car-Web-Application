package com.example.mobilele.services;

import com.example.mobilele.utils.constants.Role;
import com.example.mobilele.domain.entity.UserRole;
import com.example.mobilele.repos.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleService{

    private UserRoleRepository userRoleRepository;
    public void seed() {
        if(userRoleRepository.count() == 0){
            userRoleRepository.saveAll(List.of(
                    UserRole.builder()
                            .role(Role.USER)
                            .build()
                    , UserRole.builder()
                            .role(Role.ADMIN)
                            .build()
            ));
        }
    }

}
