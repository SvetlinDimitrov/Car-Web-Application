package com.example.mobilele.domain.dtos.user;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {

    @Schema(example = "Ivan")
    @NotBlank(message = "username must not be empty")
    private String username;

    @Schema(example = "12345")
    @NotBlank(message = "password must not be empty")
    private String password;

}
