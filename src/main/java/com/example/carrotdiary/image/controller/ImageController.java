package com.example.carrotdiary.image.controller;

import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.image.service.ImageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final JwtUtils jwtUtils;


    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(MultipartFile multipartFile) throws IOException {
        String s = imageService.uploadPostImage(multipartFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }
}
