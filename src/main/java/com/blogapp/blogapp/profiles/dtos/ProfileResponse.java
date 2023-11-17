package com.blogapp.blogapp.profiles.dtos;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id; //user id
    private String username;
    private String email;
    private String bio;
    private String img;
}
