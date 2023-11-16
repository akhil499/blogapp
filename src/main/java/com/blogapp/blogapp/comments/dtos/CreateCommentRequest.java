package com.blogapp.blogapp.comments.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
public class CreateCommentRequest {
    @Nullable
    private String title;

    @NonNull
    private String body;

}
