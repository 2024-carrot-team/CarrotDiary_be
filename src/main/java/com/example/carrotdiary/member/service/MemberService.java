package com.example.carrotdiary.member.service;

import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.member.dto.MemberResponseDto;
import com.example.carrotdiary.member.entity.MemberDetails;
import com.example.carrotdiary.member.dto.MemberRequestDto;
import com.example.carrotdiary.member.entity.MemberEntity;
import com.example.carrotdiary.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtil;

    //C
    public void createMember(MemberRequestDto memberRequestDto) {
        MemberEntity memberEntity = MemberEntity.builder()
                .email(memberRequestDto.email())
                .password(passwordEncoder.encode(memberRequestDto.password()))
                .nickname(memberRequestDto.nickname())
                .brithDayTime(memberRequestDto.birthDayTime())
                .role(memberRequestDto.role())
                .build();

        memberRepository.save(memberEntity);

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Member cannot be found"));

        return new MemberDetails(member);
    }

    //R 단건조회
    public MemberResponseDto checkMemberDetails(String email) throws UsernameNotFoundException{
        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Member cannot be found"));

        return MemberResponseDto.fromEntity(member);
    }


    //U 수정
    public void updateMember(String email, MemberRequestDto.updateRequestDto updateRequestDto) {
        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("can not find member"));

        member.updateMember(updateRequestDto);

        MemberResponseDto.fromEntity(member);
    }

    //D 삭제

    public void deleteMember(String email) {

        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() ->new EntityNotFoundException("entity not found"));

        memberRepository.delete(member);
    }


}
