package com.example.carrotdiary.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    @Getter
    public static class updatePostDto {

        private String title;
        private Long imageId;

        public updatePostDto(String title, Long imageId) {
            this.title = title;
            this.imageId = imageId;
        }
    }

}
