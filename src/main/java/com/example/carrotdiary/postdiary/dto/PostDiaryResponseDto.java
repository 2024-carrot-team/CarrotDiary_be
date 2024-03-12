package com.example.carrotdiary.postdiary.dto;

import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryDto;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import java.util.List;
import java.util.stream.Collectors;
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

    @Getter
    public static class PostDiaryDto {
        private Long postDiaryId;
        private List<DiaryDto> diaries;

        public PostDiaryDto(PostDiary postDiary) {
            postDiaryId = postDiary.getId();
            diaries = postDiary.getDiaries().stream()
                    .map(DiaryDto::new)
                    .collect(Collectors.toList());

        }
    }

}
