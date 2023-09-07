package com.example.mobilele.services;

import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.constants.Role;
import com.example.mobilele.domain.dtos.user.UserEditDto;
import com.example.mobilele.domain.dtos.user.UserLoginDro;
import com.example.mobilele.domain.dtos.user.UserRegisterDto;
import com.example.mobilele.domain.dtos.user.UserView;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.UserNotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.UserRepository;
import com.example.mobilele.repos.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImp extends SeedService{

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User getById(String userId) throws UserNotFoundException {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public Boolean freeToUseUsername(String username){
        return userRepository.findByUsername(username).isEmpty();
    }

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
                        .password(passwordEncoder.encode("12345"))
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
                        .password(passwordEncoder.encode("12345"))
                        .username("Ivan")
                        .userRoles(userRoleRepository.findAll())
                        .build()
        ));
    }

    public List<UserView> getAllUsers() {
        return userRepository.findAll().stream().map(UserView::new).toList();
    }

    public void register(UserRegisterDto userRegisterDto) {
        User user = userRegisterDto.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoles(List.of(userRoleRepository.findByRole(Role.USER)));
        userRepository.save(user);
    }

    public UserPrincipal login(UserLoginDro userLoginDro) throws WrongCredentialsException {
        Optional<User> user = userRepository.findByUsername(userLoginDro.getUsername());

        if(user.isPresent() && passwordEncoder.matches(userLoginDro.getPassword() , user.get().getPassword())){
            return new UserPrincipal(user.get());
        }
        throw new WrongCredentialsException("Username or password does not match");
    }

    @Transactional
    public UserView editUser(UserEditDto dto , String id) throws UserNotFoundException {
        User user = getById(id);

        user.setUsername(dto.getUsername());
        user.setImageUrl(dto.getImageUrl());
        user.setFirstName(dto.getFirstName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setLastName(dto.getLastName());

        User save = userRepository.save(user);

        return new UserView(save);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
