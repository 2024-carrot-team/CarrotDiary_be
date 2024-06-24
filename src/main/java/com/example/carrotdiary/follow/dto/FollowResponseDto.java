package com.example.carrotdiary.follow.dto;

import com.example.carrotdiary.member.entity.Member;

//팔로워용 dto
public record FollowResponseDto(
        String email,
        String nickname
) {
    public static FollowResponseDto from(Member member) {
        return new FollowResponseDto(member.getEmail(), member.getNickname());
    }
}