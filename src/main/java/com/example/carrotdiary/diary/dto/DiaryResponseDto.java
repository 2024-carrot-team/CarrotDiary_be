package com.example.carrotdiary.diary.dto;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.image.dto.ImageInfo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {

    @Getter
    public static class DiaryContentDto {
        private Long memberId;
        private Long postDiaryId;
        private String content;
        private LocalDateTime diaryDate;

        public DiaryContentDto(Diary diary) {
            memberId = diary.getPostDiary().getPost().getMember().getId();
            postDiaryId = diary.getPostDiary().getId();
            content = diary.getContent();
            diaryDate = diary.getCreatDate();
        }
    }

    @Getter
    public static class DiaryDto {
        private Long memberId;
        private Long diaryId;
        private String content;
        private List<ImageInfo> imageInfo;


        public DiaryDto(Diary diary) {
            memberId = diary.getPostDiary().getPost().getMember().getId();
            diaryId = diary.getId();
            content = diary.getContent();
            imageInfo = diary.getImages().stream()
                    .map(image -> new ImageInfo(image.getId(), image.getImageUrl()))
                    .collect(Collectors.toList());
        }

    }

    @Getter
    public static class DiaryMainDto {
        private Long diaryId;
        private String content;
        private List<ImageInfo> imageInfo;


        public DiaryMainDto(Diary diary) {
            diaryId = diary.getId();
            content = diary.getContent();
            imageInfo = diary.getImages().stream()
                    .map(image -> new ImageInfo(image.getId(), image.getImageUrl()))
                    .collect(Collectors.toList());
        }

    }

    @Getter
    public static class DiaryIdDto {
        private Long diaryId;

        public DiaryIdDto(Long diaryId) {
            this.diaryId = diaryId;
        }
    }
}
