package com.example.carrotdiary.entity;

import static jakarta.persistence.FetchType.LAZY;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    @JoinColumn(name = "post_diary_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany
    private List<Diary> diaries = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setPost(Post post) {
        this.post = post;
        post.getPostDiary().add(this);
    }


    // 생성 메서드
    public static PostDiary createPostDiary(Post post) {
        PostDiary postDiary = new PostDiary();
        postDiary.setPost(post);

        return postDiary;
    }


}