package com.blogapp.blogapp.articles;

import com.blogapp.blogapp.articles.dtos.CreateArticleRequest;
import com.blogapp.blogapp.articles.dtos.UpdateArticleRequest;
import com.blogapp.blogapp.users.UsersRepository;
import com.blogapp.blogapp.users.UsersService;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articlesRepository.findBySlug(slug);
        if(article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest req, Long authorId) {

        var author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));

        return articlesRepository.save(ArticleEntity.builder()
                .title(req.getTitle())
                //TODO: Create a proper slugification function
                .slug(req.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .body(req.getBody())
                .subtitle(req.getSubtitle())
                .author(author)
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest req) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));
        if(req.getTitle() != null) {
            article.setTitle(req.getTitle());
            article.setSlug(req.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }
        if(req.getBody() != null) {
            article.setBody(req.getBody());
        }
        if(req.getSubtitle() != null) {
            article.setSubtitle(req.getSubtitle());
        }

        return articlesRepository.save(article);
    }

    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found");
        }

        public ArticleNotFoundException(Long articleId) {
            super("Article with Id: " + articleId + " not found");
        }
    }
}