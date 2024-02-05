package com.example.carrotdiary.member.repository;

import com.example.carrotdiary.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
