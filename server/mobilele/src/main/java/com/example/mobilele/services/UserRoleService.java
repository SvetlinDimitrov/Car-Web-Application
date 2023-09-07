package com.example.mobilele.services;

import com.example.mobilele.domain.constants.Role;
import com.example.mobilele.domain.entity.UserRole;
import com.example.mobilele.repos.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserRoleService extends SeedService {

    private UserRoleRepository userRoleRepository;

    @Override
    protected Boolean isEmpty() {
        return userRoleRepository.count() == 0;
    }

    @Override
    protected void seed() {
        userRoleRepository.saveAll(List.of(
                UserRole.builder()
                        .role(Role.USER)
                        .build()
                ,UserRole.builder()
                        .role(Role.ADMIN)
                        .build()
        ));
    }
}
