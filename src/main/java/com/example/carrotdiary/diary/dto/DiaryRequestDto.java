package com.example.carrotdiary.diary.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {

    @Getter
    public static class CreateDiaryDto {
        private String content;

        public CreateDiaryDto(String content) {
            this.content = content;
        }


    }
    @Getter
    public static class UpdateDiaryDto {


        private String content;
        private List<Long> deleteImageIds;


        public UpdateDiaryDto(String content, List<Long> deleteImageIds) {
            this.content = content;
            this.deleteImageIds = deleteImageIds;
        }
    }



}
