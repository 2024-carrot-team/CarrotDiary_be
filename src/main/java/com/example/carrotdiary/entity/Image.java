package com.example.carrotdiary.entity;

import static jakarta.persistence.FetchType.LAZY;

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
public class Image {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    // 연관관계 편의 메서드
    public void setDiary(Diary diary) {
        this.diary = diary;
        diary.getImages().add(this);
    }

    // 생성 메서드
    public static Image addImage(String imageUrl, Diary diary) {
        Image image = new Image();
        image.imageUrl = imageUrl;
        image.setDiary(diary);

        return image;
    }

}
