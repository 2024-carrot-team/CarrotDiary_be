package com.example.carrotdiary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;

@Entity
@Getter
public class PostDiary {

    @Id
    @GeneratedValue
    @JoinColumn(name = "post_diary_id")
    private Long id;

    // @ManyToOne(fetch = LAZY)
    // @JoinColumn(name = "post_id")
    // private Post post;
}