package com.example.carrotdiary.post.dto;

import com.example.carrotdiary.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long postId;
    private String title;
    private String imageUrl;

    public PostResponseDto(Post post) {
        postId = post.getId();
        title = post.getTitle();
        imageUrl = post.getImage().getImageUrl();
    }
}
