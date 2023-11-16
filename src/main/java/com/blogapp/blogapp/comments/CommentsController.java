package com.blogapp.blogapp.comments;

import com.blogapp.blogapp.comments.dtos.CreateCommentRequest;
import com.blogapp.blogapp.users.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentsController {

    private final CommentsService commentsService;

    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("")
    String getComments() {


        return "comments";
    }

    @PostMapping("")
    ResponseEntity<String> createComment(@AuthenticationPrincipal UserEntity user, @PathVariable("articleId") Long articleId, @RequestBody CreateCommentRequest req) {
        CommentEntity createdComment = commentsService.createComment(req, user.getId(), articleId);

        return ResponseEntity.ok("Comment is published");
    }


}
