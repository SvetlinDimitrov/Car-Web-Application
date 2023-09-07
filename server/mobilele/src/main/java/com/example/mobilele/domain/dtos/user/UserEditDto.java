package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.exceptions.usernameChecker.NotUsedUsernameConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserEditDto {
    @Size(min = 4)
    @NotBlank
    @NotUsedUsernameConstraint
    private String username;

    @Size(min = 5)
    @NotBlank
    private String password;

    @Size(min = 3)
    @NotBlank
    private String firstName;

    @Size(min = 3)
    @NotBlank
    private String lastName;

    private String imageUrl;
}
