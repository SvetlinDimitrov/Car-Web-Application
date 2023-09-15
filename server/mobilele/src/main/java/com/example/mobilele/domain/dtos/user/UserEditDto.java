package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.validators.usernameChecker.NotUsedUsernameConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {

    @NotUsedUsernameConstraint(message = "username already excited in the data")
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String imageUrl;
}
