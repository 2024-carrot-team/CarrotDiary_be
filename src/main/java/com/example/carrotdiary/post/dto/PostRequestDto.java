package com.example.carrotdiary.post.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostRequestDto {
    private String title;

    public PostRequestDto(String title) {
        this.title = title;

    }
}
