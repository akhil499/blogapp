package com.blogapp.blogapp.comments;

import com.blogapp.blogapp.comments.dtos.CommentsResponse;
import com.blogapp.blogapp.comments.dtos.CreateCommentRequest;
import com.blogapp.blogapp.users.UserEntity;
import com.blogapp.blogapp.users.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles/{articleId}/comments")
public class CommentsController {

    private final CommentsService commentsService;
    private final ModelMapper modelMapper;
    private final UsersService usersService;

    public CommentsController(CommentsService commentsService, ModelMapper modelMapper, UsersService usersService) {
        this.commentsService = commentsService;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
    }

    @GetMapping("")
    ResponseEntity<Iterable<CommentsResponse>> getComments(@PathVariable("articleId") Long articleId, @AuthenticationPrincipal UserEntity user) {

        Iterable<CommentEntity> comments = commentsService.getAllComments(articleId);

        List<CommentsResponse> commentsResponse = new ArrayList<>();
        for (CommentEntity comment : comments) {
            UserEntity commentWriter = usersService.getUser(comment.getAuthorId());

            CommentsResponse response = modelMapper.map(comment, CommentsResponse.class);
            response.setAuthorName(commentWriter.getUsername());
            commentsResponse.add(response);
        }
        return ResponseEntity.ok(commentsResponse);
    }

    @PostMapping("")
    ResponseEntity createComment(@AuthenticationPrincipal UserEntity user, @PathVariable("articleId") Long articleId, @RequestBody CreateCommentRequest req) {
        CommentEntity createdComment = commentsService.createComment(req, user.getId(), articleId);

        return ResponseEntity.noContent().build();
    }


}
