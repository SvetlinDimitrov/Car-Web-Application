package com.example.mobilele.services;

import com.example.mobilele.domain.entity.User;
import com.example.mobilele.repos.UserRepository;
import com.example.mobilele.repos.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImp extends SeedService{

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;

    @Override
    protected Boolean isEmpty() {
        return userRepository.count() == 0;
    }

    @Override
    protected void seed() {
        userRepository.saveAll(List.of(
                User.builder()
                        .isActive(Boolean.TRUE)
                        .created(LocalDate.now())
                        .firstName("Zahari")
                        .lastName("Stoqnov")
                        .imageUrl("https://yt3.googleusercontent.com/ytc/AOPolaROJsray4rabTdqph4zpBJAt_01EwS5FbVlNfus=s900-c-k-c0x00ffffff-no-rj")
                        .modified(null)
                        .password("12345")
                        .username("Zahari")
                        .userRoles(userRoleRepository.findAll())
                        .build(),
                User.builder()
                        .isActive(Boolean.TRUE)
                        .created(LocalDate.now())
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/2/27/Ivan_Abadjiev.jpg")
                        .modified(null)
                        .password("12345")
                        .username("Ivan")
                        .userRoles(userRoleRepository.findAll())
                        .build()
        ));
    }
}
