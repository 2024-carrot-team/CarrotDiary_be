package com.example.carrotdiary.image.dto;

import com.example.carrotdiary.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageResponseDto {

    private String fileName;
    private String imageUrl;
    private Member member;

    public ImageResponseDto(String fileName, String imageUrl) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }



}
