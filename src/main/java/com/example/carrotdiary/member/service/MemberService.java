package com.example.carrotdiary.member.service;

import com.example.carrotdiary.image.service.ImageService;
import com.example.carrotdiary.member.dto.MemberRequestDto;
import com.example.carrotdiary.member.dto.MemberResponseDto;
import com.example.carrotdiary.member.entity.MemberDetails;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    //C
    public void createMember(MemberRequestDto memberRequestDto, MultipartFile pictureFile) throws IOException {
        Member member = Member.builder()
                .email(memberRequestDto.email())
                .password(passwordEncoder.encode(memberRequestDto.password()))
                .nickname(memberRequestDto.nickname())
                .brithDayTime(memberRequestDto.birthDayTime())
                .role(memberRequestDto.role())
                .build();
        memberRepository.save(member);

        member.setImageInMemberEntity(imageService.uploadProfileImage(pictureFile, member));

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Member cannot be found"));

        return new MemberDetails(member);
    }

    //R 단건조회
    public MemberResponseDto checkMemberDetails(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Member cannot be found"));

        return MemberResponseDto.fromEntity(member);
    }

    public MemberResponseDto findByIdMember(Long id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("entity not found"));

        return MemberResponseDto.fromEntity(member);
    }

    //U 수정
    public void updateMember(String email, MemberRequestDto.updateRequestDto updateRequestDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("can not find member"));

        member.updateMember(updateRequestDto);

        MemberResponseDto.fromEntity(member);
    }

    //D 삭제

    public void deleteMember(String email) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("entity not found"));

        memberRepository.delete(member);
    }

    public MemberResponseDto findProfile(String email) {
        return new MemberResponseDto(memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("entity not found")));
    }
}
