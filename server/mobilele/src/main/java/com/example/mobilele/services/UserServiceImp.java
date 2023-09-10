package com.example.mobilele.services;

import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.entity.UserRole;
import com.example.mobilele.utils.EntityHelper;
import com.example.mobilele.utils.constants.Role;
import com.example.mobilele.domain.dtos.user.UserEditDto;
import com.example.mobilele.domain.dtos.user.UserLoginDro;
import com.example.mobilele.domain.dtos.user.UserRegisterDto;
import com.example.mobilele.domain.dtos.user.UserView;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.repos.UserRepository;
import com.example.mobilele.repos.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
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
public class UserServiceImp{

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityHelper entityHelper;

    public User getById(String userId) throws NotFoundException, WrongCredentialsException {
        return entityHelper.findUserById(userId);
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

        if (user.isPresent() && passwordEncoder.matches(userLoginDro.getPassword(), user.get().getPassword())) {
            return new UserPrincipal(user.get());
        }
        throw new WrongCredentialsException("Username or password does not match");
    }

    @Modifying
    public UserView editUser(UserEditDto dto, String id) throws NotFoundException, WrongCredentialsException {
        User user = getById(id);


        if (dto.getUsername() != null) {
            if (dto.getUsername().trim().length() >= 4) {
                user.setUsername(dto.getUsername());
            } else {
                throw new WrongCredentialsException("username must be at least 4 chars long");
            }
        }
        if (dto.getImageUrl() != null) {
            user.setImageUrl(dto.getImageUrl());
        }
        if (dto.getFirstName() != null) {
            if (dto.getFirstName().trim().length() >= 3) {
                user.setFirstName(dto.getFirstName());
            } else {
                throw new WrongCredentialsException("firstName must be at least 3 chars long");
            }
        }
        if (dto.getPassword() != null) {
            if (dto.getPassword().trim().length() >= 5) {
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
            } else {
                throw new WrongCredentialsException("password must be at least 5 chars long");
            }
        }
        if (dto.getLastName() != null) {
            if (dto.getLastName().trim().length() >= 4) {
                user.setLastName(dto.getLastName());
            } else {
                throw new WrongCredentialsException("lastName must be at least 4 chars long");
            }
        }

        user.setModified(LocalDate.now());
        User save = userRepository.save(user);

        return new UserView(save);
    }

    @Modifying
    public void deleteUser(String id) throws NotFoundException, WrongCredentialsException {
        User user = getById(id);
        userRepository.delete(user);
    }
    @Modifying
    public void deleteUserById(String id) throws NotFoundException, WrongCredentialsException {
        User user = entityHelper.findUserById(id);
        userRepository.delete(user);
    }

    public boolean isAdmin(User user){
        return user.getUserRoles().stream().map(UserRole::getRole).anyMatch(r-> r.equals(Role.ADMIN));
    }
    public void seed() {
        if(userRepository.count() == 0){
            userRepository.saveAll(List.of(
                    User.builder()
                            .isActive(Boolean.TRUE)
                            .created(LocalDate.now())
                            .firstName("Zaharia")
                            .lastName("Stepanov")
                            .imageUrl("https://yt3.googleusercontent.com/ytc/AOPolaROJsray4rabTdqph4zpBJAt_01EwS5FbVlNfus=s900-c-k-c0x00ffffff-no-rj")
                            .modified(null)
                            .password(passwordEncoder.encode("12345"))
                            .username("Zaharia")
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
    }
}
