package com.example.carrotdiary.post.dto;

import com.example.carrotdiary.image.dto.ImageResponseDto;
import com.example.carrotdiary.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {


    private Long id;
    private String title;
    private String imageUrl;

    public PostResponseDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        imageUrl = post.getImage().getImageUrl();
    }
}
