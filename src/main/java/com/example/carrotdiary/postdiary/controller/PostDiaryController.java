package com.example.carrotdiary.postdiary.controller;

import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.postdiary.service.PostDiaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostDiaryController {

    private final PostDiaryService postDiaryService;
    private final JwtUtils jwtUtils;

    @PostMapping("/postDiary/{postId}")
    public ResponseEntity<Long> createPostDiary(HttpServletRequest req, @PathVariable("postId") Long postId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }
        Long postDiaryId = postDiaryService.createPostDiary(postId);

        return ResponseEntity.ok(postDiaryId);

    }


    @DeleteMapping("/postDiary/{postDiaryId}")
    public ResponseEntity<String> deletePostDiary(@PathVariable("postDiaryId") Long postDiaryId,
                                                  HttpServletRequest req) {
        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        postDiaryService.deletePostDiary(postDiaryId);
        return ResponseEntity.noContent().build();

    }
}


