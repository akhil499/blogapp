package com.blogapp.blogapp.articles;

import com.blogapp.blogapp.articles.dtos.ArticleResponse;
import com.blogapp.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.blogapp.articles.dtos.UpdateArticleRequest;
import com.blogapp.blogapp.commons.dtos.ErrorResponse;
import com.blogapp.blogapp.users.UserEntity;
import com.blogapp.blogapp.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        ArticleEntity article =  articlesService.getArticleById(id);
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
    ResponseEntity createArticle(@AuthenticationPrincipal UserEntity user, @RequestBody CreateArticleRequest req) {

        ArticleEntity createdArticle = articlesService.createArticle(req, user.getId());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{slug}")
    ResponseEntity<String> editArticle(@AuthenticationPrincipal UserEntity user, @PathVariable("slug") String slug, @RequestBody UpdateArticleRequest req) {

        ArticleEntity createdArticle = articlesService.updateArticle(slug, req);
        return ResponseEntity.ok("Article with Id " + createdArticle.getId() + " and slug " + createdArticle.getSlug() + " is updated"); //TODO: slug it return the latest one need to return the earlier one.
        //TODO: For edit title, subtitle and body is mandatory params, make them optional.
    }

    @ExceptionHandler({
            ArticlesService.ArticleNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleArticleNotFoundException(Exception ex) {

        String message;
        HttpStatus status;

        if(ex instanceof ArticlesService.ArticleNotFoundException) {
            message = ex.getMessage();
            status = HttpStatus.NOT_FOUND;
        }  else
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
