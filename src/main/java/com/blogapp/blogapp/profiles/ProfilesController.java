package com.blogapp.blogapp.profiles;

import com.blogapp.blogapp.commons.dtos.ErrorResponse;
import com.blogapp.blogapp.profiles.dtos.ProfileResponse;
import com.blogapp.blogapp.users.UserEntity;
import com.blogapp.blogapp.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfilesController {

    //TODO: Handle errors properly.

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public ProfilesController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    ResponseEntity<Iterable<ProfileResponse>> getProfiles() {
        Iterable<UserEntity> users = usersService.getAllUsers();

        List<ProfileResponse> usersResponses = new ArrayList<>();
        for (UserEntity user : users) {
            ProfileResponse response = modelMapper.map(user, ProfileResponse.class);
            usersResponses.add(response);
        }

        return ResponseEntity.ok(usersResponses);
    }

    @GetMapping("/{username}")
    ResponseEntity<ProfileResponse> getProfileByUsername(@PathVariable("username") String username) {
        UserEntity user = usersService.getUser(username);

        ProfileResponse response = modelMapper.map(user, ProfileResponse.class);

        return ResponseEntity.ok(response);
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
