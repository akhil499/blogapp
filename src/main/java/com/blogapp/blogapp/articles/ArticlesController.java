package com.blogapp.blogapp.articles;

import com.blogapp.blogapp.articles.dtos.ArticleResponse;
import com.blogapp.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.blogapp.users.UserEntity;
import com.blogapp.blogapp.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticlesService articlesService;
    private final ModelMapper modelMapper;
    private final UsersService usersService;

    public ArticlesController(ArticlesService articlesService, ModelMapper modelMapper, UsersService usersService) {
        this.articlesService = articlesService;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
    }

    @GetMapping("")
    ResponseEntity<Iterable<ArticleResponse>> getArticles() {

        Iterable<ArticleEntity> articles = articlesService.getAllArticles();

        List<ArticleResponse> articleResponses = new ArrayList<>();
        for (ArticleEntity article : articles) {
            ArticleResponse response = modelMapper.map(article, ArticleResponse.class);
            articleResponses.add(response);
        }

        return ResponseEntity.ok(articleResponses);
    }


    @GetMapping("/{id}")
    ResponseEntity<ArticleResponse> getArticleById(@PathVariable("id") long id) {
        Optional<ArticleEntity> article =  articlesService.getArticleById(id);
        var articleResponse = modelMapper.map(article, ArticleResponse.class);

        return ResponseEntity.ok(articleResponse);
    }

    @GetMapping("/slug/{slug}")
    ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable("slug") String slug) {
        ArticleEntity article = (ArticleEntity) articlesService.getArticleBySlug(slug);

        var articleResponse = modelMapper.map(article, ArticleResponse.class);

        return ResponseEntity.ok(articleResponse);
    }

    @PostMapping("")
    ResponseEntity<String> createArticle(@AuthenticationPrincipal UserEntity user, @RequestBody CreateArticleRequest req) {

        ArticleEntity createdArticle = articlesService.createArticle(req, user.getId());

        return ResponseEntity.ok("Article with Id " + createdArticle.getId() + " and title " + createdArticle.getTitle() + " is published");
    }
}
