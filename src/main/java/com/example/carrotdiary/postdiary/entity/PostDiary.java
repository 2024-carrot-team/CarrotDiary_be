package com.example.carrotdiary.postdiary.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.post.entity.Post;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class PostDiary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_diary_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "postDiary", cascade = CascadeType.ALL)
    private List<Diary> diaries = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setPost(Post post) {
        this.post = post;
        post.getPostDiary().add(this);
    }

    // 생성 메서드
    public static PostDiary addPostDiary(Post post) {
        PostDiary postDiary = new PostDiary();
        postDiary.setPost(post);

        return postDiary;
    }


}