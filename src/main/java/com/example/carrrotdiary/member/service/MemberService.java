package com.example.carrrotdiary.member.service;

import com.example.carrrotdiary.member.dto.MemberRequestDto;
import com.example.carrrotdiary.member.dto.MemberResponseDto;
import com.example.carrrotdiary.member.entity.MemberEntity;
import com.example.carrrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    //C
    public MemberResponseDto CreateMember(MemberRequestDto memberRequestDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .nickname(memberRequestDto.getNickname())
                .brithDayTime(memberRequestDto.getBrithDayTime())
                .role(memberRequestDto.getRole())
                .build();

        memberRepository.save(memberEntity);
        return MemberResponseDto.fromEntity(memberEntity);
    }


    //R 전체조회


    //R 단건조회


    //U 수정


    //D 삭제
}
