package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.exceptions.passwordChecker.PasswordsMatchConstrain;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@PasswordsMatchConstrain

public class UserLoginDro extends UserBaseDto{

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    public UserLoginDro(String password, String confirmPassword, String username) {
        super(password, confirmPassword, username);
    }
}
