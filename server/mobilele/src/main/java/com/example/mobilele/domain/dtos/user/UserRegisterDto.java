package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.User;
import com.example.mobilele.validators.passwordChecker.PasswordsMatchConstrain;
import com.example.mobilele.validators.usernameChecker.NotUsedUsernameConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordsMatchConstrain
public class UserRegisterDto{

    @Size(min = 4, message = "username must be at least 4 chars long")
    @NotBlank(message = "username must not be empty")
    @NotUsedUsernameConstraint(message = "username already excited in the data")
    private String username;

    @Size(min = 5 , message = "password must be at least 5 chars long")
    @NotBlank(message = "password must not be empty")
    private String password;

    @NotBlank(message = "confirmPassword must not be empty")
    private String confirmPassword;

    @Size(min = 3 ,message = "firstName must be at least 3 chars long")
    @NotBlank(message = "firstName must not be empty")
    private String firstName;

    @Size(min = 3 ,message = "lastName must be at least 3 chars long")
    @NotBlank(message = "lastName must not be empty")
    private String lastName;

    private String imageUrl;

    public User toUser(){
        return User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(false)
                .imageUrl(imageUrl == null ? "https://cdn-icons-png.flaticon.com/512/149/149071.png" : imageUrl)
                .created(LocalDate.now())
                .modified(null)
                .build();
    }
}
