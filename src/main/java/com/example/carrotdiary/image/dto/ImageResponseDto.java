package com.example.carrotdiary.image.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDto {

    private String fileName;
    private String imageUrl;

    public ImageResponseDto(String fileName, String imageUrl) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }


}
