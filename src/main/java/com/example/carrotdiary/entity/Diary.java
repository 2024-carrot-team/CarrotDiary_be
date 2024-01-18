package com.example.carrotdiary.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Diary {

    @Id
    @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    private PostDiary postDiary;
}