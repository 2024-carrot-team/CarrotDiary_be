//package com.example.carrotdiary.follow.service;
//
//import com.example.carrotdiary.follow.dto.FollowResponseDto;
//import com.example.carrotdiary.follow.entity.Follow;
//import com.example.carrotdiary.follow.repository.FollowRepository;
//import com.example.carrotdiary.member.dto.MemberDetailDto;
//import com.example.carrotdiary.member.entity.Member;
//import com.example.carrotdiary.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class FollowService {
//
//    private final MemberRepository memberRepository;
//    private final FollowRepository followRepository;
//
//    public void follow(String followerRequest, String followingRequest) {
//        // 과연 Member Entity를 Follow 도메인에서 관리하는게 맞는가 ?
//        // Follow 도메인은 Member 도메인에 들어가야하려나? 조금 고민해보자.
//
//        Member follower = getToMember(followerRequest);
//
//        Member following = getFromMember(followingRequest);
//
//
//        if (follower == following || follower == null || following == null) {
//            throw new IllegalArgumentException("");
//        } else {
//            Follow follow = Follow.builder()
//                    .follower(follower)
//                    .following(following)
//                    .build();
//        }
//    }
//
//    private Member getFromMember(String followingRequest) {
//        return memberRepository.findByEmail(followingRequest).orElseThrow(()-> new IllegalArgumentException("Could not find followingRequest"));
//    }
//
//    private Member getToMember(String followRequest) {
//        return memberRepository.findByEmail(followRequest).orElseThrow(() -> new IllegalArgumentException("Could not find followRequest"));
//    }
//
//    // 팔로우 삭제
//    public void deleteFollow(String followRequest, String followingRequest) {
//        followRepository.deleteFollowByToMemberAndFromMember(getToMember(followRequest), getFromMember(followingRequest));
//    }
//
//    // follower 찾기 메소드 작성
////    public List<FollowResponseDto> findAllFollowers (String fromMemberFromRequest) {
////        List<Follow> followers = followRepository.findAllByFromMember(getFromMember(fromMemberFromRequest));
////
////    }
////
//}
