package com.example.carrrotdiary.member.service;

import com.example.carrrotdiary.global.jwt.JwtUtils;
import com.example.carrrotdiary.member.dto.MemberRequestDto;
import com.example.carrrotdiary.member.dto.MemberResponseDto;
import com.example.carrrotdiary.member.entity.MemberDetails;
import com.example.carrrotdiary.member.entity.MemberEntity;
import com.example.carrrotdiary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder;

    private JwtUtils jwtUtil;

    //C
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {
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


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Member cannot be found"));

        return new MemberDetails(member);
    }

    //R 단건조회
    public MemberResponseDto checkMemberDetails(Long id) {
        MemberEntity member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("doesn't exist username"));

        return MemberResponseDto.fromEntity(member);
    }


    //U 수정
    public MemberResponseDto updateMember(Long id, MemberRequestDto.updateRequestDto updateRequestDto) {
        MemberEntity member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("can not find member"));

        member.updateMember(updateRequestDto);

        return MemberResponseDto.fromEntity(member);
    }

    //D 삭제

    public Boolean deleteMember(Long id) {

        if (checkEntityExist(id)) {
            memberRepository.deleteById(id);
            return true;
        } else {
            return false;
        }


    }

    private Boolean checkEntityExist(Long id) {
        Optional<MemberEntity> member = memberRepository.findById(id);
        return member.isPresent();
    }
}
