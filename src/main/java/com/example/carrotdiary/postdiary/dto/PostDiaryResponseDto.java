package com.example.carrotdiary.postdiary.dto;

import lombok.Getter;

@Getter
public class PostDiaryResponseDto {

    @Getter
    public static class PostDiaryIdDto {
        private Long postDiaryId;

        public PostDiaryIdDto(Long postDiaryId) {
            this.postDiaryId = postDiaryId;
        }
    }

}
