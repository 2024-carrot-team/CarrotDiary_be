package com.example.carrrotdiary.member.dto;

import com.example.carrrotdiary.member.entity.MemberEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class MemberResponseDto {

    private String email;
    private String nickname;
    private LocalDateTime birthDayTime;

    public MemberResponseDto(MemberEntity memberEntity) {
        this.email = memberEntity.getEmail();
        this.nickname = memberEntity.getNickname();
        this.birthDayTime = memberEntity.getBrithDayTime();
    }
}
