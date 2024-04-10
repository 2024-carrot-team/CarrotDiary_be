package com.example.carrotdiary.follow.repository;

import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow deleteFollowByToMemberAndFromMember(Member toMemberFromRequest, Member fromMemberFromRequest);
}
