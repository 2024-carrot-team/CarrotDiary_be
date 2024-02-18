package com.example.carrotdiary.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {
    private String title;
    private String imageUrl;

    public PostRequestDto(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }
}
