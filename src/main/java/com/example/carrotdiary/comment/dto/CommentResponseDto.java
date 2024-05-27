package com.example.carrotdiary.comment.dto;

import com.example.carrotdiary.comment.entity.Comment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String nickname;
    private String content;
    private int childCommentCount;
    private List<CommentResponseDto> child;
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.childCommentCount = comment.getChild().size();
        this.child = comment.getChild().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

}
