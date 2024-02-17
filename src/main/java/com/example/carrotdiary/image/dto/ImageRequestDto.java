package com.example.carrotdiary.image.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ImageRequestDto {
    private MultipartFile multipartFile;
}
