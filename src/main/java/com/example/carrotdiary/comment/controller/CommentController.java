package com.example.carrotdiary.comment.controller;

import com.example.carrotdiary.comment.dto.CommentIdDto;
import com.example.carrotdiary.comment.dto.CommentRequestDto;
import com.example.carrotdiary.comment.dto.CommentUpdateDto;
import com.example.carrotdiary.comment.service.CommentService;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.common.ResultComment;
import com.example.carrotdiary.global.jwt.JwtUtils;
import com.example.carrotdiary.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
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
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    //전체 댓글수 + 답글에 대한 대댓글 수 API 추가로 보내기
    @PostMapping("/comments/{postDiaryId}")
    public ResponseEntity<CommentIdDto> createComment(HttpServletRequest request, @PathVariable Long postDiaryId,
                                                      @RequestBody CommentRequestDto commentRequestDto) {

        String userEmail = jwtUtils.getUserEmail(request);
        String nickname = memberService.findNicknameByEmail(userEmail);

        CommentIdDto commentId = commentService.createComment(postDiaryId, nickname, commentRequestDto);

        return ResponseEntity.ok().body(commentId);
    }

    @GetMapping("/comments/{postDiaryId}")
    public ResponseEntity<ResultComment> getComments(@PathVariable Long postDiaryId) {
        ResultComment comments = commentService.getComments(postDiaryId);

        return ResponseEntity.ok(comments);
    }

    @PatchMapping("/comment/{commentId}")
    public ResponseEntity<Result> updateComment(HttpServletRequest request, @PathVariable Long commentId,
                                           @RequestBody CommentUpdateDto commentUpdateDto) {

        String userEmail = jwtUtils.getUserEmail(request);
        String nickname = memberService.findNicknameByEmail(userEmail);


        Result result = commentService.updateComment(nickname, commentId, commentUpdateDto.getContent());

        return ResponseEntity.ok(result);

    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<String> deleteComment(HttpServletRequest request, @PathVariable Long commentId) {

        String userEmail = jwtUtils.getUserEmail(request);
        String nickname = memberService.findNicknameByEmail(userEmail);

        commentService.deleteComment(nickname, commentId);

        return ResponseEntity.status(HttpStatus.OK).body("Success");

    }
}
