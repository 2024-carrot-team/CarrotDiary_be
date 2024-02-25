package com.example.carrotdiary.post.controller;

import com.example.carrotdiary.diary.service.DiaryService;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.post.dto.PostRequestDto;
import com.example.carrotdiary.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final DiaryService diaryService;
    private final JwtUtils jwtUtils;

    @PostMapping("/post")
    public ResponseEntity<Long> createPost(HttpServletRequest req,
                                           @ModelAttribute PostRequestDto postRequestDto,
                                           @RequestParam MultipartFile image) throws IOException {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Long postId = postService.createPost(userEmail, postRequestDto, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @GetMapping("/post")
    public ResponseEntity<Result> getPosts(HttpServletRequest req) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result posts = postService.getPosts(userEmail);

        return ResponseEntity.ok(posts);
    }
    @GetMapping("post/{postId}")
    public ResponseEntity<Result> getPreviewDiary(HttpServletRequest req, @PathVariable("postId") Long postId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result previewDiary = diaryService.getPreviewDiary(postId);

        return ResponseEntity.ok(previewDiary);
    }



    @PatchMapping("/post/{postId}")
    public ResponseEntity<Result> updatePost(HttpServletRequest req,
                                             @PathVariable("postId") Long postId,
                                             @RequestBody PostRequestDto postRequestDto,
                                             @RequestParam(required = false) MultipartFile image) throws IOException {
        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }
        Result result = postService.updatePost(postId, postRequestDto, image);

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("post/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") Long postId, HttpServletRequest req) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }
        postService.deletePost(postId);

        return ResponseEntity.noContent().build();
    }
}
