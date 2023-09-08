package com.example.mobilele.domain.dtos.user;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserLoginDro{

    @NotBlank(message = "username must not be empty")
    private String username;

    @NotBlank(message = "password must not be empty")
    private String password;

}
