package com.example.carrotdiary.post.controller;

import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.post.dto.PostRequestDto;
import com.example.carrotdiary.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtUtils jwtUtils;

    @PostMapping("/post")
    public ResponseEntity<Long> createPost(HttpServletRequest req, @RequestBody @Valid PostRequestDto postRequestDto) {
        String userEmail = jwtUtils.getUserEmail(req);
        Long postId = postService.createPost(userEmail, postRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @GetMapping("/post")
    public ResponseEntity<Result> getPosts(HttpServletRequest req) {
        String userEmail = jwtUtils.getUserEmail(req);

        Result posts = postService.getPosts(userEmail);

        return ResponseEntity.ok(posts);
    }

    @PatchMapping("/post/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable("postId") Long postId,
                                             @RequestBody @Valid PostRequestDto postRequestDto,
                                             HttpServletRequest req) {
        String userEmail = jwtUtils.getUserEmail(req);

        if (postService.checkPostOwnership(postId, userEmail)) {
            postService.updatePost(postId, postRequestDto);
            return ResponseEntity.ok("updated Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId, HttpServletRequest req) {
        String userEmail = jwtUtils.getUserEmail(req);
        if (postService.checkPostOwnership(postId, userEmail)) {
            postService.deletePost(postId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
}
