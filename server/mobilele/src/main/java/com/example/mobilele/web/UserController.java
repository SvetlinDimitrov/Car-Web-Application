package com.example.mobilele.web;

import com.example.mobilele.config.security.JwtUtil;
import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.user.UserEditDto;
import com.example.mobilele.domain.dtos.user.UserLoginDro;
import com.example.mobilele.domain.dtos.user.UserRegisterDto;
import com.example.mobilele.domain.dtos.user.UserView;
import com.example.mobilele.exceptions.UserNotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "${api.base-url}"+ "/user")
public class UserController {

    private final UserServiceImp userServiceImp;
    private final JwtUtil jwtUtil;

    public UserController(UserServiceImp userServiceImp, JwtUtil jwtUtil) {
        this.userServiceImp = userServiceImp;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserView> userDetails(@PathVariable String userId) throws UserNotFoundException {
        UserView userView = new UserView(userServiceImp.getById(userId));
        return new ResponseEntity<>(userView, HttpStatus.OK);
    }
    @PatchMapping("/edit")
    public ResponseEntity<UserView> editUser(@Valid @RequestBody UserEditDto editDto,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) throws WrongCredentialsException, UserNotFoundException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getFieldErrors());
        }

        UserView userView = userServiceImp.editUser(editDto, userPrincipal.getId().toString());
        return new ResponseEntity<>(userView , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserView>> allUsers() {
        return new ResponseEntity<>(userServiceImp.getAllUsers() , HttpStatus.OK);
    }

    @PatchMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto,
                                                   BindingResult bindingResult) throws WrongCredentialsException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getFieldErrors());
        }
        userServiceImp.register(userRegisterDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/login")
    public ResponseEntity<UserPrincipal> loginUser(@Valid @RequestBody UserLoginDro userLoginDro,
                                                   BindingResult bindingResult) throws WrongCredentialsException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getFieldErrors());
        }
        UserPrincipal userPrincipal = userServiceImp.login(userLoginDro);
        String jwtToken = jwtUtil.createJwtToken(userPrincipal.getId());
        userPrincipal.setJwtToken(jwtToken);
        return new ResponseEntity<>(userPrincipal , HttpStatus.OK);
    }


}
