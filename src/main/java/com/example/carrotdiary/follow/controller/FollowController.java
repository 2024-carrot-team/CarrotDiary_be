package com.example.carrotdiary.follow.controller;

import com.example.carrotdiary.follow.service.FollowService;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final JwtUtils jwtUtils;
    @PostMapping("/Members/follow/{followId}")
    public ResponseEntity<String> follow(HttpServletRequest req, @PathVariable String followId) {
        followService.follow(jwtUtils.getUserEmail(req),followId);
        return ResponseEntity.ok().build();
    }
}
