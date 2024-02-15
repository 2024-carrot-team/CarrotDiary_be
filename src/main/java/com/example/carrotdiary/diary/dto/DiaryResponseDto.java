package com.example.carrotdiary.diary.dto;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.image.dto.ImageResponseDto.ImageDto;
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
        private String content;
        private List<ImageDto> images;

        public DiaryDto(Diary diary) {
            content = diary.getContent();
            images = diary.getImages().stream()
                    .map(ImageDto::new)
                    .collect(Collectors.toList());
        }

    }
}
