package com.example.carrotdiary.post.dto;

import com.example.carrotdiary.image.dto.ImageResponseDto.ImageDto;
import com.example.carrotdiary.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    @Getter
    public static class PostDto {

        private String title;
        private ImageDto image;

        public PostDto(Post post) {
            title = post.getTitle();
            image = new ImageDto(post.getImage());
        }
    }

}
