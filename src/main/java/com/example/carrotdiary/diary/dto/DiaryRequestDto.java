package com.example.carrotdiary.diary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {

    @Getter
    public static class updateDiaryDto {


        private String content;
        private Long imageId;

        public updateDiaryDto(String content, Long imageId) {
            this.content = content;
            this.imageId = imageId;
        }
    }


}
