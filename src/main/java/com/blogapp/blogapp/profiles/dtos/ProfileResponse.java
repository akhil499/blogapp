package com.blogapp.blogapp.profiles.dtos;

import lombok.Data;

@Data
public class ProfileResponse {
    private String username;
    private String email;
    private String bio;
    private String img;
}
