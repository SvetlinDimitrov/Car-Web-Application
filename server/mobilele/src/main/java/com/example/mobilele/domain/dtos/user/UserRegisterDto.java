package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.passwordChecker.PasswordsMatchConstrain;
import com.example.mobilele.exceptions.usernameChecker.NotUsedUsernameConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@PasswordsMatchConstrain

public class UserRegisterDto extends UserBaseDto{

    @Size(min = 4)
    @NotBlank
    @NotUsedUsernameConstraint
    private String username;

    @Size(min = 5)
    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @Size(min = 3)
    @NotBlank
    private String firstName;

    @Size(min = 3)
    @NotBlank
    private String lastName;

    private String imageUrl;

    public UserRegisterDto(String password, String confirmPassword, String username) {
        super(password, confirmPassword, username);
    }

    public UserRegisterDto(String password, String confirmPassword, String username, String username1, String password1, String confirmPassword1, String firstName, String lastName, String imageUrl) {
        super(password, confirmPassword, username);
        this.username = username1;
        this.password = password1;
        this.confirmPassword = confirmPassword1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public User toUser(){
        return User.builder()
                .username(username)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(false)
                .imageUrl(imageUrl.isEmpty() ? "https://cdn-icons-png.flaticon.com/512/149/149071.png" : imageUrl)
                .created(LocalDate.now())
                .modified(null)
                .build();
    }
}
