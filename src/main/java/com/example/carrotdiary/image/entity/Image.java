package com.example.carrotdiary.image.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.diary.entity.Diary;
import com.example.carrotdiary.global.common.BaseEntity;
import com.example.carrotdiary.post.entity.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;
    private String fileName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    // 연관관계 편의 메서드
    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getImages().add(this);
    }

    public Image(String imageUrl, String fileName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }

    // 생성 메서드
    public static Image addDiaryImage(String imageUrl, String fileName, Diary diary) {
        Image image = new Image();
        image.imageUrl = imageUrl;
        image.fileName = fileName;
        image.setDiary(diary);

        return image;
    }

    public static Image addPostImage(String imageUrl, String fileName, Post post) {
        Image image = new Image();
        image.imageUrl = imageUrl;
        image.fileName = fileName;
        post.setPostImage(image);

        return image;
    }

    public void updateImage(String imageUrl, String fileName) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }


}
