package com.example.carrotdiary.diary.dto;

import com.example.carrotdiary.image.dto.ImageInfo;
import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiaryMainFlatDto {

    private Long diaryId;
    private String content;
    private List<ImageInfo> imageInfo = new ArrayList<>();

    @QueryProjection
    public DiaryMainFlatDto(Long diaryId, String content) {
        this.diaryId = diaryId;
        this.content = content;
    }

}
