package com.blogapp.blogapp.users;

import com.blogapp.blogapp.security.JWTService;
import com.blogapp.blogapp.users.dtos.CreateUserRequest;
import com.blogapp.blogapp.users.dtos.LoginUserRequest;
import com.blogapp.blogapp.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blogapp.blogapp.commons.dtos.ErrorResponse;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;
    private final JWTService jwtService;

    public UsersController(UsersService usersService, ModelMapper modelMapper, JWTService jwtService) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }


    @PostMapping("")
    ResponseEntity<UserResponse> signupUser(@RequestBody CreateUserRequest req) {
        UserEntity savedUser = usersService.createUser(req);
        URI savedUserUri = URI.create("/users/" + savedUser.getId());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(
                jwtService.createJWT(savedUser.getId())
        );

        return ResponseEntity.created(savedUserUri)
                .body(userResponse);
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> loginUser(@RequestBody LoginUserRequest req) {
        UserEntity savedUser = usersService.loginUser(req.getUsername(), req.getPassword());
        var userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setToken(
                jwtService.createJWT(savedUser.getId())
        );

        return ResponseEntity.ok(userResponse);
    }

    @ExceptionHandler({
            UsersService.UserNotFoundException.class,
            UsersService.InvalidCredentialsException.class
    })
    ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex) {

        String message;
        HttpStatus status;

        if(ex instanceof UsersService.UserNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        } else if(ex instanceof UsersService.InvalidCredentialsException) {
            message = ex.getMessage();
            status = HttpStatus.UNAUTHORIZED;
        } else
         {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }


        ErrorResponse response = ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }
}
