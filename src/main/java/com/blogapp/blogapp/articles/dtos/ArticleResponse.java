package com.blogapp.blogapp.articles.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleResponse {
    private long id;
    private String title;
    private String slug;
    private String subtitle;
    private String body;
    private Date createdAt;
    private String authorName;
}
