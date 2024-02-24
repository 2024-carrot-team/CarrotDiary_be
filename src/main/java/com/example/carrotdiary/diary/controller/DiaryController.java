package com.example.carrotdiary.diary.controller;

import com.example.carrotdiary.diary.dto.DiaryRequestDto;
import com.example.carrotdiary.diary.service.DiaryService;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final JwtUtils jwtUtils;

    @PostMapping("/diary/{postDiaryId}")
    public ResponseEntity<Long> createDiary(HttpServletRequest req, @PathVariable Long postDiaryId,
                                            @RequestBody DiaryRequestDto.createDiaryDto diaryDto,
                                            @RequestPart List<MultipartFile> images) throws IOException {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Long diaryId = diaryService.createDiary(postDiaryId, diaryDto.getContent(), images);

        return ResponseEntity.status(HttpStatus.CREATED).body(diaryId);

    }

    @GetMapping("/diary/all/{postDiaryId}")
    public ResponseEntity<Result> getAllDiary(HttpServletRequest req, @PathVariable("postDiaryId") Long postDiaryId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result diaries = diaryService.getAllDiary(postDiaryId);

        return ResponseEntity.ok(diaries);

    }

    @GetMapping("/diary/detail/{diaryId}")
    public ResponseEntity<Result> getDiary(HttpServletRequest req, @PathVariable("diaryId") Long diaryId) {

        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result diary = diaryService.getDiary(diaryId);

        return ResponseEntity.ok(diary);
    }

    @PatchMapping("/diary/{diaryId}")
    public ResponseEntity<Result> updateDiary(HttpServletRequest req, @PathVariable("diaryId") Long diaryId,
                                              @RequestBody DiaryRequestDto.updateDiaryDto updateDiaryDto,
                                              @RequestPart(required = false) List<MultipartFile> images) throws IOException {
        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        Result result = diaryService.updateDiary(diaryId, updateDiaryDto, images);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<String> deleteDiary(HttpServletRequest req, @PathVariable("diaryId") Long diaryId) {
        String userEmail = jwtUtils.getUserEmail(req);
        if (userEmail == null) {
            throw new IllegalArgumentException("need login");
        }

        diaryService.deleteDiary(diaryId);

        return ResponseEntity.noContent().build();
    }
}
