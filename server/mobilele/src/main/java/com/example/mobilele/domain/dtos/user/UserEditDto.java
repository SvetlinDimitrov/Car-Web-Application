package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.exceptions.usernameChecker.NotUsedUsernameConstraint;
import lombok.Data;

@Data
public class UserEditDto {

    @NotUsedUsernameConstraint(message = "username already excited in the data")
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String imageUrl;
}
