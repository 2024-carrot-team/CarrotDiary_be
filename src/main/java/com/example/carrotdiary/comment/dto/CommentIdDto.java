package com.example.carrotdiary.comment.dto;

import com.example.carrotdiary.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentIdDto {
    private Long commentId;

    public CommentIdDto(Comment comment) {
        this.commentId = comment.getId();
    }
}
