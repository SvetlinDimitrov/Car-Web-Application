package com.example.mobilele.web;

import com.example.mobilele.config.security.JwtUtil;
import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.user.*;
import com.example.mobilele.exceptions.NotFoundException;
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

    @GetMapping()
    public ResponseEntity<UserView> userDetails(@AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {
        UserView userView = new UserView(userServiceImp.getById(principal.getId()));
        return new ResponseEntity<>(userView, HttpStatus.OK);
    }
    @PatchMapping("/edit")
    public ResponseEntity<UserView> editUser(@Valid @RequestBody UserEditDto editDto,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) throws WrongCredentialsException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }

        UserView userView = userServiceImp.editUser(editDto, userPrincipal.getId());
        return new ResponseEntity<>(userView , HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserView>> allUsers() {
        return new ResponseEntity<>(userServiceImp.getAllUsers() , HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto,
                                                   BindingResult bindingResult) throws WrongCredentialsException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }
        userServiceImp.register(userRegisterDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserPrincipalDto> loginUser(@Valid @RequestBody UserLoginDro userLoginDro,
                                                   BindingResult bindingResult) throws WrongCredentialsException {
        if(bindingResult.hasErrors()){
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }

        UserPrincipal userPrincipal = userServiceImp.login(userLoginDro);
        String jwtToken = jwtUtil.createJwtToken(userPrincipal.getId());
        userPrincipal.setJwtToken(jwtToken);

        return new ResponseEntity<>(new UserPrincipalDto(userPrincipal) , HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {
        userServiceImp.deleteUser(principal.getId());
        return new ResponseEntity<>(principal.getUsername() +"was successfuly deleted",HttpStatus.NO_CONTENT);
    }


}
