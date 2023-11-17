package com.blogapp.blogapp.comments;

import com.blogapp.blogapp.articles.ArticlesRepository;
import com.blogapp.blogapp.comments.dtos.CreateCommentRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final ArticlesRepository articlesRepository;

    public CommentsService(CommentsRepository commentsRepository, ArticlesRepository articlesRepository) {
        this.commentsRepository = commentsRepository;
        this.articlesRepository = articlesRepository;
    }

    public Iterable<CommentEntity> getAllComments(Long articleId) {
        Iterable<CommentEntity> comments = commentsRepository.findByArticleId(articleId);
        return comments;
    }

      public CommentEntity createComment(CreateCommentRequest req, Long authorId, Long articleId) {
            return commentsRepository.save(
                    CommentEntity.builder()
                            .title(req.getTitle())
                            .createdAt(new Date())
                            .body(req.getBody())
                            .articleId(articleId)
                            .authorId(authorId)
                            .build()
            );
      }
}
