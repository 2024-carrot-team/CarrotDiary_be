package com.example.carrrotdiary.member.dto;

import com.example.carrrotdiary.member.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@RequiredArgsConstructor
public class MemberResponseDto {

    private final String email;
    private final String nickname;
    private final LocalDateTime birthDayTime;

    public MemberResponseDto(MemberEntity memberEntity) {
        this.email = memberEntity.getEmail();
        this.nickname = memberEntity.getNickname();
        this.birthDayTime = memberEntity.getBrithDayTime();
    }

    public static MemberResponseDto fromEntity(MemberEntity memberEntity){

        return MemberResponseDto.builder()
                .email(memberEntity.getEmail())
                .nickname(memberEntity.getNickname())
                .birthDayTime(memberEntity.getBrithDayTime())
                .build();

    }
}
