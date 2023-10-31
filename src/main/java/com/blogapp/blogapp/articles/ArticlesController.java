package com.blogapp.blogapp.articles;

import com.blogapp.blogapp.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    @GetMapping("")
    String getArticles() {
        return "get all articles";
    }

    @GetMapping("/{id}")
    String getArticleById(@PathVariable("id") String id) {
        return "get article with id " + id;
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntity user) {
        return "Create article called by " + user.getUsername();
    }
}
