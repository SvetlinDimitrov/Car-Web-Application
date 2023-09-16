package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.validators.usernameChecker.NotUsedUsernameConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEditDto {

    @Schema(minLength = 4, description = "username is a unique value", example = "Pesho")
    @NotUsedUsernameConstraint(message = "username already excited in the data")
    private String username;

    @Schema(minLength = 5 , example = "12345")
    private String password;

    @Schema(minLength = 3 , example = "Pesho")
    private String firstName;

    @Schema(minLength = 3 , example = "Peshov")
    private String lastName;

    private String imageUrl;
}
