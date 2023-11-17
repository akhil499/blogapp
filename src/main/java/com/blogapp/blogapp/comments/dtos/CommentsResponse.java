package com.blogapp.blogapp.comments.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class CommentsResponse {
    private Long id;
    private String title;
    private String body;
    private Date createdAt;
    //private Long articleId;
    private Long authorId;
    private String authorName;
}
