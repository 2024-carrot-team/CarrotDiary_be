package com.example.carrotdiary.postdiary.dto;

import com.example.carrotdiary.diary.dto.DiaryMainFlatDto;
import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDiaryFlatDto {

    private Long memberId;
    private String nickname;
    private String memberProfileImageUrl;
    private Long postId;
    private Long postDiaryId;
    private List<DiaryMainFlatDto> diaries = new ArrayList<>();

    @QueryProjection
    public PostDiaryFlatDto(Long postDiaryId, Long postId, Long memberId, String nickname, String memberProfileImageUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.memberProfileImageUrl = memberProfileImageUrl;
        this.postId = postId;
        this.postDiaryId = postDiaryId;
    }
}
