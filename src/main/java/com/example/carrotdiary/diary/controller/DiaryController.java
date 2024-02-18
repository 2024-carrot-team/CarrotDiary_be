package com.example.carrotdiary.diary.controller;

import com.example.carrotdiary.diary.dto.DiaryRequestDto;
import com.example.carrotdiary.diary.service.DiaryService;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.image.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final ImageService imageService;
    private final JwtUtils jwtUtils;

    @PostMapping("/diary")
    public ResponseEntity<Result> createDiary(HttpServletRequest req,
                                         @ModelAttribute DiaryRequestDto.createDiaryDto diaryDto,
                                         @RequestParam List<MultipartFile> images) throws IOException {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        List<Image> uploadImages = imageService.uploadImages(images);

        Result diary = diaryService.createDiary(diaryDto.getPostDiaryId(), diaryDto.getContent(), uploadImages);

        return ResponseEntity.status(HttpStatus.CREATED).body(diary);

    }

    @GetMapping("/diary/{postDiaryId}")
    public ResponseEntity<Result> getAllDiary(HttpServletRequest req, @PathVariable("postDiaryId") Long postDiaryId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result diaries = diaryService.getAllDiary(postDiaryId);

        return ResponseEntity.ok(diaries);

    }

    @GetMapping("/diary/{diaryId}")
    public ResponseEntity<Result> getDiary(HttpServletRequest req, @PathVariable("diaryId") Long diaryId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result diary = diaryService.getDiary(diaryId);

        return ResponseEntity.ok(diary);
    }

    @GetMapping("diary/{postId}")
    public ResponseEntity<Result> getPreviewDiary(HttpServletRequest req, @PathVariable("postId") Long postId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result previewDiary = diaryService.getPreviewDiary(postId);

        return ResponseEntity.ok(previewDiary);
    }

//    @PatchMapping("/diary/{diaryId}")
//    public ResponseEntity<?> updateDiary(@PathVariable("diaryId") Long diaryId, DiaryRequestDto) {
//
//    } 이미지 수정 부분 생각

    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<String> deleteDiary(HttpServletRequest req, @PathVariable("diaryId") Long diaryId) {
        String userEmail = jwtUtils.getUserEmail(req);

        diaryService.deleteDiary(diaryId);

        return ResponseEntity.noContent().build();
    }
}
