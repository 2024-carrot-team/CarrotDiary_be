package com.example.carrotdiary.follow.controller;

import com.example.carrotdiary.follow.dto.FollowResponseDto;
import com.example.carrotdiary.follow.service.FollowService;
import com.example.carrotdiary.global.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Members/follow/")
public class FollowController {

    private final FollowService followService;
    private final JwtUtils jwtUtils;

    //팔로우 Id를 path로 받을지 dto로 받을지 좀 고민해봐야할거같음
    @PostMapping("{followEmail}")
    public ResponseEntity<String> follow(HttpServletRequest req, @PathVariable String followEmail) {
        followService.follow(followEmail,jwtUtils.getUserEmail(req));
        return ResponseEntity.ok().build();
    }

    //삭제 컨트롤러 코드 추가
    @DeleteMapping("{followEmail}")
    public ResponseEntity<String> unfollow(HttpServletRequest req, @PathVariable String followEmail) {
        followService.unfollow(jwtUtils.getUserEmail(req), followEmail);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("followers")
    public ResponseEntity<List<FollowResponseDto>> getFollowers(HttpServletRequest req) {
        String email = jwtUtils.getUserEmail(req);
        List<FollowResponseDto> followers = followService.getFollowers(email);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("followings")
    public ResponseEntity<List<FollowResponseDto>> getFollowings(HttpServletRequest req) {
        String email = jwtUtils.getUserEmail(req);
        List<FollowResponseDto> followings = followService.getFollowings(email);
        return ResponseEntity.ok(followings);
    }

}