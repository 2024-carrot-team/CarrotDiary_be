package com.example.carrotdiary.image.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.global.common.BaseEntity;
import com.example.carrotdiary.member.entity.Member;
import com.example.carrotdiary.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;
    private String fileName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 연관관계 편의 메서드
    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getImages().add(this);
    }

    public Image(String fileName, String imageUrl) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }

    public void updateImage(String fileName, String imageUrl) {
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }

    public static Image addUserImage(String fileName, String imageUrl, Member member) {
        Image image = new Image();
        image.fileName = fileName;
        image.imageUrl = imageUrl;
        image.setMemberInImageEntity(member);

        return image;
    }

    public void setMemberInImageEntity(Member member) {
        this.member = member;
    }
}
