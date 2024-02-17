package com.example.carrrotdiary.member.controller;

import com.example.carrrotdiary.member.dto.MemberRequestDto;
import com.example.carrrotdiary.member.dto.MemberResponseDto;
import com.example.carrrotdiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<String> signUp(MemberRequestDto memberRequestDto) {
            memberService.createMember(memberRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    // security 구현 후 토큰에서 유저 이메일 꺼내오는 로직
    // 수정 해야함
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMember(MemberRequestDto memberRequestDto, String email) {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkMemberDetails(email));

    }

    @PatchMapping
    public ResponseEntity<String> updateMember(MemberRequestDto.updateRequestDto memberRequestDto, String email){
        memberService.updateMember(email, memberRequestDto);

        return ResponseEntity.ok("updated Successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(String email) {
        memberService.deleteMember(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("deleted Successfully");
    }



}
