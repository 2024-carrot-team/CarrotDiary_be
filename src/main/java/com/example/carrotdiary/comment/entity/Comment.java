package com.example.carrotdiary.comment.entity;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_diary_id")
    private PostDiary postDiary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> child = new ArrayList<>();

    public static Comment createComment(PostDiary postDiary, String nickname, String content, Comment parent) {
        Comment comment = new Comment();
        comment.postDiary = postDiary;
        comment.nickname = nickname;
        comment.content = content;
        comment.parent = parent;

        return comment;
    }

    public void updateComment(String content) {
        this.content = content;
    }



}
