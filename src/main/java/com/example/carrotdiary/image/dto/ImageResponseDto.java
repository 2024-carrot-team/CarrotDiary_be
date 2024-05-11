package com.example.carrotdiary.image.dto;

import lombok.Getter;

@Getter
public class ImageResponseDto {

    private String fileName;
    private String imageUrl;

    public ImageResponseDto(String fileName, String imageUrl) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }



}
