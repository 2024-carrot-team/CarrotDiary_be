package com.example.carrotdiary.postdiary.dto;

import com.example.carrotdiary.diary.entity.DiarySearch;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PostDiaryRequestDto {

    @Getter
    @AllArgsConstructor
    public static class PostDiarySearchDto {
        private DiarySearch diarySearch;
        private String searchContent;

    }
}
