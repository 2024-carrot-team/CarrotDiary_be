package com.example.carrotdiary.follow.service;

import com.example.carrotdiary.follow.dto.FollowResponseDto;
import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.follow.repository.FollowRepository;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    //팔로우 대상  - followee
    //팔로우 하는 사람 - follower
    public void follow(String followerEmail, String followeeEmail) {
        Member follower = getMemberByEmail(followerEmail);
        Member followee = getMemberByEmail(followeeEmail);

        if (follower.equals(followee)) {
            throw new IllegalArgumentException("자기 자신을 팔로우 할 수 없습니다");
        }
        // 중복된 팔로우 관계가 있는지 확인
        if (followRepository.findByFollowerAndFollowee(follower, followee).isPresent()) {
            throw new IllegalStateException("이미 팔로우중인 유저입니다.");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(followee)
                .build();

        followRepository.save(follow);
    }

    public void unfollow(String followerEmail, String followeeEmail) {
        Member follower = getMemberByEmail(followerEmail);
        Member followee = getMemberByEmail(followeeEmail);

        followRepository.deleteFollowByFollowerAndFollowee(follower, followee);
    }

    public List<FollowResponseDto> getFollowers(String email) {
        Member member = getMemberByEmail(email);
        List<Follow> follows = followRepository.findByFollowee(member);

        return follows.stream()
                .map(follow -> FollowResponseDto.from(follow.getFollower()))
                .collect(Collectors.toList());
    }

    public List<FollowResponseDto> getFollowings(String email) {
        Member member = getMemberByEmail(email);
        List<Follow> follows = followRepository.findByFollower(member);

        return follows.stream()
                .map(follow -> FollowResponseDto.from(follow.getFollowee()))
                .collect(Collectors.toList());
    }
    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Could not find member with email: " + email));
    }

    //팔로우 중인지 아닌지 확인하는 로직
    //추후 게시글의 팔로우 공개 로직에 사용하면 될듯?
    public boolean isFollowing(String followerEmail, String followingEmail) {
        Member follower = getMemberByEmail(followerEmail);
        Member following = getMemberByEmail(followingEmail);

        return followRepository.findByFollowerAndFollowee(follower, following).isPresent();
    }
}