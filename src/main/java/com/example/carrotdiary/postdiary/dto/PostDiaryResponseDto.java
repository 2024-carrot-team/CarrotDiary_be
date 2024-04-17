package com.example.carrotdiary.postdiary.dto;

import com.example.carrotdiary.diary.dto.DiaryResponseDto.DiaryMainDto;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostDiaryResponseDto {

    @Getter
    public static class PostDiaryIdDto {
        private Long memberId;
        private Long postDiaryId;

        public PostDiaryIdDto(PostDiary postDiary) {
            this.memberId = postDiary.getPost().getMember().getId();
            this.postDiaryId = postDiary.getId();
        }

    }

    @Getter
    public static class PostDiaryDto {
        private Long memberId;
        private String nickname;
        private Long postId;
        private Long postDiaryId;
        private List<DiaryMainDto> diaries;

        public PostDiaryDto(PostDiary postDiary) {
            memberId = postDiary.getPost().getMember().getId();
            nickname = postDiary.getPost().getMember().getNickname();
            postId = postDiary.getPost().getId();
            postDiaryId = postDiary.getId();
            diaries = postDiary.getDiaries().stream()
                    .map(DiaryMainDto::new)
                    .collect(Collectors.toList());

        }
    }

    @Getter
    public static class PostDiaryDtoV2 {
        private Long memberId;
        private Long postId;
        private Long postDiaryId;
        private List<DiaryMainDto> diaries;

        public PostDiaryDtoV2(PostDiary postDiary) {
            memberId = postDiary.getPost().getMember().getId();
            postId = postDiary.getPost().getId();
            postDiaryId = postDiary.getId();
            diaries = postDiary.getDiaries().stream()
                    .map(DiaryMainDto::new)
                    .collect(Collectors.toList());
        }

    }

}
