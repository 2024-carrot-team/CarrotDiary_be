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
import lombok.Getter;

@Entity
@Getter
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


}