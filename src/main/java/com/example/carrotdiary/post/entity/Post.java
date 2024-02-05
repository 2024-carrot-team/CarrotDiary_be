package com.example.carrotdiary.post.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.common.BaseTimeEntity;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostDiary> postDiary;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    // 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setPostImage(Image image) {
        this.image = image;
    }

    // 생성 메서드
    public static Post addPost(Member member, String title, Image image) {
        Post post = new Post();
        post.title = title;
        post.setMember(member);
        post.setPostImage(image);

        return post;
    }

    public void updatePost(String title, Image image) {
        this.title = title;
        this.image = image;
    }
}