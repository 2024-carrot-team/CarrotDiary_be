package com.example.carrotdiary.post.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.member.entity.MemberEntity;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostDiary> postDiary;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    // 연관관계 편의 메서드
    public void setMember(MemberEntity member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setPostImage(Image image) {
        this.image = image;
    }

    // 생성 메서드
    public static Post addPost(MemberEntity member, String title, Image image) {
        Post post = new Post();
        post.title = title;
        post.setMember(member);
        post.setPostImage(image);

        return post;
    }

    public void updatePostTitle(String title) {
        this.title = title;
    }

    public void updatePostImage(Image image) {
        this.image = image;
    }
}