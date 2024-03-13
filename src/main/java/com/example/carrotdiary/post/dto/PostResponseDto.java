package com.example.carrotdiary.post.dto;

import com.example.carrotdiary.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {

    private Long memberId;
    private Long postId;
    private String title;
    private String imageUrl;

    public PostResponseDto(Post post) {
        memberId = post.getMember().getId();
        postId = post.getId();
        title = post.getTitle();
        imageUrl = post.getImage().getImageUrl();
    }

    @Getter
    public static class PostIdDto {
        private Long postId;
        public PostIdDto(Post post) {
            this.postId = post.getId();
        }
    }

}
