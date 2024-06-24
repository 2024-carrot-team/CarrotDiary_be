package com.example.carrotdiary.follow.repository;

import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로잉하는 사용자의 목록을 찾기
    List<Follow> findByFollower(Member following);

    // 팔로워 목록을 찾기
    List<Follow> findByFollowee(Member following);

    Follow deleteFollowByFollowerAndFollowee(Member followersRequest, Member followingsRequest);

    Optional<Follow> findByFollowerAndFollowee(Member follower, Member following);

}