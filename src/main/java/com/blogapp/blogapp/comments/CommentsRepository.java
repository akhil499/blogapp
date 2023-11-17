package com.blogapp.blogapp.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
    Iterable<CommentEntity> findByArticleId(Long articleId);
}
