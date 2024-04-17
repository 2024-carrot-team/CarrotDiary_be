package com.example.carrotdiary.postdiary.controller;

import com.example.carrotdiary.diary.entity.DiarySearch;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.postdiary.dto.PostDiaryRequestDto.PostDiarySearchDto;
import com.example.carrotdiary.postdiary.dto.PostDiaryResponseDto.PostDiaryIdDto;
import com.example.carrotdiary.postdiary.service.PostDiaryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostDiaryController {

    private final PostDiaryService postDiaryService;
    private final JwtUtils jwtUtils;

    @PostMapping("/postDiary/{postId}")
    public ResponseEntity<PostDiaryIdDto> createPostDiary(HttpServletRequest req, @PathVariable("postId") Long postId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }
        PostDiaryIdDto postDiaryId = postDiaryService.createPostDiary(postId);

        return ResponseEntity.ok(postDiaryId);

    }

    @GetMapping("/main")
    public ResponseEntity<Result> getMainDiary(HttpServletRequest req,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "5") int size) {
        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result mainPostDiaries = postDiaryService.getMainPostDiaries(page, size);

        return ResponseEntity.ok(mainPostDiaries);
    }

    @GetMapping("/search")
    public ResponseEntity<Result> getPostDiariesBySearch(HttpServletRequest req,
                                                         @RequestBody PostDiarySearchDto postDiarySearchDto,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "5") int size) {


        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Pageable pageable = PageRequest.of(page, size);

        Result postDiariesBySearch = postDiaryService.getPostDiariesBySearch(postDiarySearchDto, pageable);

        return ResponseEntity.ok(postDiariesBySearch);
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


