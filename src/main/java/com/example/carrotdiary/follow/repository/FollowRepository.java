package com.example.carrotdiary.follow.repository;

import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로잉하는 사용자의 목록을 찾기
    List<Follow> findByFollower(Member following);

    // 팔로워 목록을 찾기
    List<Follow> findByFollowing(Member following);

    Follow deleteFollowByFollowerAndFollowing(Member followersRequest, Member followingsRequest);

    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);

}