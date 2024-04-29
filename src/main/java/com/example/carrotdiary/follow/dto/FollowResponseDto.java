package com.example.carrotdiary.follow.dto;

import com.example.carrotdiary.member.entity.Member;

//팔로워용 dto
public record FollowResponseDto(
        Member follower
){
}
