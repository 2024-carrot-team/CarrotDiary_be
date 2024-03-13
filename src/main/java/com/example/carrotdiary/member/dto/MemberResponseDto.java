package com.example.carrotdiary.member.dto;

import com.example.carrotdiary.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@RequiredArgsConstructor
public class MemberResponseDto {

    private final String email;
    private final String nickname;
    private final LocalDateTime birthDayTime;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.birthDayTime = member.getBrithDayTime();
    }

    public static MemberResponseDto fromEntity(Member member){

        return MemberResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthDayTime(member.getBrithDayTime())
                .build();

    }
}
