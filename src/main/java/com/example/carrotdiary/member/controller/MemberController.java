package com.example.carrotdiary.member.controller;

import com.example.carrotdiary.member.dto.MemberRequestDto;
import com.example.carrotdiary.member.dto.MemberResponseDto;
import com.example.carrotdiary.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping
    public ResponseEntity<String> signUp(@RequestBody MemberRequestDto memberRequestDto) throws IOException {
            memberService.createMember(memberRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }

    // security 구현 후 토큰에서 유저 이메일 꺼내오는 로직
    // 수정 해야함
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMember(@RequestBody String email) {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkMemberDetails(email));

    }

    @GetMapping("/findMember/{memberId}")
    public ResponseEntity<MemberResponseDto> findMemberById(@RequestBody Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findByIdMember(id));
    }

    @PatchMapping
    public ResponseEntity<String> updateMember(@RequestBody MemberRequestDto.updateRequestDto memberRequestDto, String email){
        memberService.updateMember(email, memberRequestDto);

        return ResponseEntity.ok("updated Successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMember(@RequestBody String email) {
        memberService.deleteMember(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("deleted Successfully");
    }

    @GetMapping("/userProfile")
    public ResponseEntity<MemberResponseDto> findProfile(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findProfile(email));
    }


}
