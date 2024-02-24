package com.example.carrotdiary.diary.dto;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.image.entity.Image;
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
        private String content;
        private LocalDateTime diaryDate;

        public DiaryContentDto(Diary diary) {
            content = diary.getContent();
            diaryDate = diary.getCreatDate();
        }
    }

    @Getter
    public static class DiaryDto {
        private Long id;
        private String content;
        private List<Long> imageId;
        private List<String> imageUrls;

        public DiaryDto(Diary diary) {
            id = diary.getId();
            content = diary.getContent();
            imageId = diary.getImages().stream()
                    .map(Image::getId)
                    .collect(Collectors.toList());
            imageUrls = diary.getImages().stream()
                    .map(Image::getImageUrl)
                    .collect(Collectors.toList());

        }

    }
}
