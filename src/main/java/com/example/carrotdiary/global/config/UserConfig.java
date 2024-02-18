package com.example.carrrotdiary.global.config;

import com.example.carrrotdiary.global.constants.Role;
import com.example.carrrotdiary.member.dto.MemberRequestDto;
import com.example.carrrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserConfig {

    @Value("${jwt.token}")
    private String ADMIN_TOKEN;

    private final MemberRepository memberRepository;

    public Role checkRole(MemberRequestDto requestDto) {
        Role role = Role.USER;
        if(requestDto.isAdmin() && requestDto.adminToken().equals(ADMIN_TOKEN)){
            role = Role.ADMIN;
        }
        return role;
    }

    public void checkEmail(String email) {
        if(memberRepository.existsByEmail(email)){
            throw new IllegalArgumentException("해당 이메일은 이미 존재합니다");
        }
    }

}