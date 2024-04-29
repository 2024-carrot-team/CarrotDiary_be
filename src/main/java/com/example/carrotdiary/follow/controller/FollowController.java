package com.example.carrotdiary.follow.controller;

import com.example.carrotdiary.follow.service.FollowService;
import com.example.carrotdiary.global.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final JwtUtils jwtUtils;

    //팔로우 Id를 path로 받을지 dto로 받을지 좀 고민해봐야할거같음
    @PostMapping("/Members/follow/{followId}")
    public ResponseEntity<String> follow(HttpServletRequest req, @PathVariable String followId) {
        followService.follow(followId,jwtUtils.getUserEmail(req));
        return ResponseEntity.ok().build();
    }

    //삭제 컨트롤러 코드 추가
    @DeleteMapping("/Members/follow/{followId}")
    public ResponseEntity<String> unfollow(HttpServletRequest req, @PathVariable String followId) {
        followService.deleteFollow(jwtUtils.getUserEmail(req),followId);
        return ResponseEntity.noContent().build();
    }
}