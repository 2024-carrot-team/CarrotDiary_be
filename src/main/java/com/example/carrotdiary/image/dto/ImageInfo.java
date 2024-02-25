package com.example.carrotdiary.image.dto;

import lombok.Getter;

@Getter
public class ImageInfo {
    private Long imageId;
    private String imageUrl;

    public ImageInfo(Long imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }
}
