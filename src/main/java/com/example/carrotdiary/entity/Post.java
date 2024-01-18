package com.example.carrotdiary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Getter;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    // @ManyToOne(fetch = LAZY) // 이 부분을 제거합니다.
    // @JoinColumn(name = "member_id")
    // private Member member;

    @OneToMany(mappedBy = "post")
    private List<PostDiary> postDiary;
}