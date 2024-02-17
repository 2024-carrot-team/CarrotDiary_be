package com.example.carrotdiary.image.dto;

import com.example.carrotdiary.image.entity.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDto {

    private String imageUrl;

    public ImageResponseDto(Image image) {
        imageUrl = image.getImageUrl();
    }
}
