package com.example.carrotdiary.follow.service;

import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.follow.repository.FollowRepository;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public void follow(String toMemberFromRequest, String fromMemberFromRequest) {
        // 과연 Member Entity를 Follow 도메인에서 관리하는게 맞는가 ?
        // Follow 도메인은 Member 도메인에 들어가야하려나? 조금 고민해보자.

        Member toMember = getToMember(toMemberFromRequest);

        Member fromMember = getFromMember(fromMemberFromRequest);


        if (toMember == fromMember || toMember == null || fromMember == null) {
            throw new IllegalArgumentException("");
        } else {
            Follow follow = Follow.builder()
                    .toMember(toMember)
                    .fromMember(fromMember)
                    .build();
        }
    }

    private Member getFromMember(String fromMemberFromRequest) {
        return memberRepository.findByEmail(fromMemberFromRequest).orElseThrow(()-> new IllegalArgumentException("Could not find fromMemberFromRequest"));
    }

    private Member getToMember(String toMemberFromRequest) {
        return memberRepository.findByEmail(toMemberFromRequest).orElseThrow(() -> new IllegalArgumentException("Could not find toMemberFromRequest"));
    }

    // 팔로우 삭제
    public void deleteFollow(String toMemberFromRequest, String fromMemberFromRequest) {
        followRepository.deleteFollowByToMemberAndFromMember(getToMember(toMemberFromRequest), getFromMember(fromMemberFromRequest));
    }
    
}
