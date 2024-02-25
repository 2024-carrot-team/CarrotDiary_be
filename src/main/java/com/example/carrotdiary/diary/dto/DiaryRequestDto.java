package com.example.carrotdiary.diary.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {

    @Getter
    public static class createDiaryDto {
        private String content;

        public createDiaryDto(String content) {
            this.content = content;
        }


    }
    @Getter
    public static class updateDiaryDto {


        private String content;
        private List<Long> deleteImageIds;


        public updateDiaryDto(String content, List<Long> deleteImageIds) {
            this.content = content;
            this.deleteImageIds = deleteImageIds;
        }
    }



}
