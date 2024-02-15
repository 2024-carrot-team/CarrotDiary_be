package com.example.carrotdiary.diary.entity;

import static jakarta.persistence.FetchType.LAZY;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.postdiary.entity.PostDiary;
import jakarta.persistence.Column;
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
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    private String content;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_diary_id")
    private PostDiary postDiary;

    @OneToMany(mappedBy = "diary")
    private List<Image> images = new ArrayList<>();

    // 연관관계 편의 메서드
    public void setPostDiary(PostDiary postDiary) {
        this.postDiary = postDiary;
        postDiary.getDiaries().add(this);
    }

    public void setDiaryImage(Image image) {
        images.add(image);
        image.setDiary(this);
    }

    // 생성 메서드
    public static Diary addDiary(PostDiary postDiary, String content, Image... images) {
        Diary diary = new Diary();
        diary.setPostDiary(postDiary);
        diary.content = content;

        for (Image image : images) {
            diary.setDiaryImage(image);
        }

        return diary;
    }

}