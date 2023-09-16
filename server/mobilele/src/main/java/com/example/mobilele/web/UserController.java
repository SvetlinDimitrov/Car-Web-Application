package com.example.mobilele.web;

import com.example.mobilele.config.security.JwtUtil;
import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.user.*;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.UserServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-url}" + "/user")
public class UserController {

    private final UserServiceImp userServiceImp;
    private final JwtUtil jwtUtil;

    public UserController(UserServiceImp userServiceImp, JwtUtil jwtUtil) {
        this.userServiceImp = userServiceImp;
        this.jwtUtil = jwtUtil;
    }


    @Operation(
            summary = "Get user/users",
            description = "Retrieve an UserView object using 'id',  or retrieve all existing users by using the 'all' parameter." +
                    "If you don't pass any parameters, you will retrieve the current user.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you don't pass a parameter, it will provide the current user",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = UserView.class)),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "all",
                            description = "A boolean parameter to retrieve all users from the database.",
                            example = "true"
                    ),
                    @Parameter(
                            name = "id",
                            description = "The user's ID to retrieve.",
                            example = "1"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserView>> getUser(@AuthenticationPrincipal UserPrincipal principal,
                                                  @RequestParam(value = "all", required = false) Boolean all,
                                                  @RequestParam(value = "id", required = false) String id) throws NotFoundException, WrongCredentialsException {
        if (all != null && all) {
            return new ResponseEntity<>(userServiceImp.getAllUsers(), HttpStatus.OK);
        } else if (id != null) {
            return new ResponseEntity<>(List.of(new UserView(userServiceImp.getById(id))), HttpStatus.OK);
        }
        return new ResponseEntity<>(List.of(new UserView(userServiceImp.getById(principal.getId()))), HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Edit offer",
            description = "If you don't send an ID, the changes will be applied to the currently authenticated user.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you send an empty body the request will be successful",
                            content = @Content(
                                    schema = @Schema(implementation = UserView.class),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The user's ID to change.",
                            example = "1"
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<UserView> editUser(@Valid @RequestBody UserEditDto editDto,
                                             BindingResult bindingResult,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestParam(value = "id", required = false) String id) throws WrongCredentialsException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }

        if (id != null) {
            if (userServiceImp.isAdmin(userServiceImp.getById(userPrincipal.getId()))) {
                UserView userView = userServiceImp.editUser(editDto, id);
                return new ResponseEntity<>(userView, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        UserView userView = userServiceImp.editUser(editDto, userPrincipal.getId());
        return new ResponseEntity<>(userView, HttpStatus.OK);
    }

    @Operation(
            summary = "Register user",
            description = "The creation of the user.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto,
                                                   BindingResult bindingResult) throws WrongCredentialsException {
        if (bindingResult.hasErrors()) {
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }
        userServiceImp.register(userRegisterDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login user",
            description = "When the login is successful, you will receive a bearer token for authentication.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = UserPrincipalDto.class),
                                    mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<UserPrincipalDto> loginUser(@Valid @RequestBody UserLoginDto userLoginDto,
                                                      BindingResult bindingResult) throws WrongCredentialsException {
        if (bindingResult.hasErrors()) {
            throw new WrongCredentialsException(bindingResult.getAllErrors());
        }

        UserPrincipal userPrincipal = userServiceImp.login(userLoginDto);
        String jwtToken = jwtUtil.createJwtToken(userPrincipal.getId());
        userPrincipal.setJwtToken(jwtToken);

        return new ResponseEntity<>(new UserPrincipalDto(userPrincipal), HttpStatus.OK);
    }


    @Operation(
            summary = "Delete offer",
            description = "You can pass an ID as a parameter to delete a user, but only if you have the admin role.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete a user",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "If you don't have the admin role, you cannot delete other users.",
                            content = @Content(mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "Only for admins to delete other users.",
                            example = "2"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserPrincipal principal,
                                             @RequestParam(value = "id", required = false) String id) throws NotFoundException, WrongCredentialsException {
        if (id != null) {
            if (userServiceImp.isAdmin(userServiceImp.getById(principal.getId()))) {
                userServiceImp.deleteUser(id);
                return new ResponseEntity<>("User with id : " + id + " was successfuly deleted", HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied ! Only Admins can do that.");
            }
        }
        userServiceImp.deleteUser(principal.getId());
        return new ResponseEntity<>(principal.getUsername() + "was successfuly deleted", HttpStatus.NO_CONTENT);
    }


}
