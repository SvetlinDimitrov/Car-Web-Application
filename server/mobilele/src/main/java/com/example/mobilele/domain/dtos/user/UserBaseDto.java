package com.example.mobilele.domain.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class UserBaseDto {
    public String password;
    public String confirmPassword;
    public String username;
}
