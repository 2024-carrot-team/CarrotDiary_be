package com.example.carrotdiary.post.dto;

import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private Long imageId;

    public PostRequestDto(String title, Long imageId) {
        this.title = title;
        this.imageId = imageId;
    }
}
