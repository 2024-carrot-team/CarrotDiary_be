package com.example.carrotdiary.member.controller;

import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.member.dto.MemberRequestDto;
import com.example.carrotdiary.member.dto.MemberResponseDto;
import com.example.carrotdiary.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<String> signUp(
            @RequestParam("member") String memberJson,
            @RequestParam("pictureFile") MultipartFile pictureFile) throws IOException {

        MemberRequestDto memberRequestDto = objectMapper.readValue(memberJson, MemberRequestDto.class);

        memberService.createMember(memberRequestDto, pictureFile);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }


    // 요청에서 이메일 가져오도록 수정했음
    // 내 프로필 조회에서 문제가 생긴다면 이 부분에서 문제가 생긴것임.
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMember(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkMemberDetails(jwtUtils.getUserEmail(request)));

    }

    @GetMapping("/findMember")
    public ResponseEntity<MemberResponseDto> findMemberById(@RequestBody Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findByIdMember(id));
    }

    @PatchMapping
    public ResponseEntity<String> updateMember(@RequestBody MemberRequestDto.updateRequestDto memberRequestDto, String email) {
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
