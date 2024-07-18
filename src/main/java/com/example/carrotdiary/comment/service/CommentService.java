package com.example.carrotdiary.comment.service;

import com.example.carrotdiary.comment.dto.CommentIdDto;
import com.example.carrotdiary.comment.dto.CommentRequestDto;
import com.example.carrotdiary.comment.dto.CommentResponseDto;
import com.example.carrotdiary.comment.entity.Comment;
import com.example.carrotdiary.comment.repository.CommentRepository;
import com.example.carrotdiary.global.common.Result;
import com.example.carrotdiary.global.common.ResultComment;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import com.example.carrotdiary.postdiary.repository.PostDiaryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostDiaryRepository postDiaryRepository;

    public CommentIdDto createComment(Long postDiaryId, String nickname, CommentRequestDto commentRequestDto) {
        PostDiary postDiary = postDiaryRepository.findById(postDiaryId)
                .orElseThrow(() -> new NoSuchElementException("조회된 것이 없습니다."));

        Comment parent = null;
        if (commentRequestDto.getParentId() != null) {
            parent = commentRepository.findById(commentRequestDto.getParentId())
                    .orElseThrow(() -> new NoSuchElementException("조회된 것이 없습니다."));
        }

        Comment comment = Comment.createComment(postDiary, nickname, commentRequestDto.getContent(), parent);

        commentRepository.save(comment);

        return new CommentIdDto(comment);
    }

    @Transactional(readOnly = true)
    public Result getComment(Long commentId) {
        Comment comment = findByCommentId(commentId);
        CommentResponseDto result = new CommentResponseDto(comment);

        return new Result(result);
    }
    @Transactional(readOnly = true)
    public ResultComment getComments(Long postDiaryId) {
        List<Comment> comments = commentRepository.findParentWithChildByPostDiaryId(postDiaryId);

        List<CommentResponseDto> result = comments.stream()
                .map(CommentResponseDto::new)
                .toList();

        int totalComments = calculateTotalComments(comments);
        return new ResultComment(totalComments, result);
    }

    public Result updateComment(String nickname, Long commentId, String newContent) {
        Comment comment = findByCommentId(commentId);
        validateComment(nickname, comment);

        comment.updateComment(newContent);

        CommentResponseDto result = new CommentResponseDto(comment);

        return new Result(result);

    }

    public void deleteComment(String nickname, Long commentId) {

        Comment comment = findByCommentId(commentId);

        validateComment(nickname, comment);

        commentRepository.delete(comment);
    }

    private void validateComment(String nickname, Comment comment) {
        if (!nickname.equals(comment.getNickname())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
    }

    private Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("조회된 것이 없습니다."));
    }

    private int calculateTotalComments(List<Comment> comments) {
        int count = 0;
        for (Comment comment : comments) {
            count++;
            count += calculateTotalComments(comment.getChild());
        }
        return count;
    }
}
