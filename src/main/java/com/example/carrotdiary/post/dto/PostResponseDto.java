package com.example.carrotdiary.post.dto;

import com.example.carrotdiary.image.dto.ImageResponseDto;
import com.example.carrotdiary.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    @Getter
    public static class PostDto {

        private String title;
        private ImageResponseDto imageUrl;

        public PostDto(Post post) {
            title = post.getTitle();
            imageUrl = new ImageResponseDto(post.getImage());
        }
    }

}
