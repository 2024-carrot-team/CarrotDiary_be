package com.example.carrotdiary.follow.entity;

import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "follower")
    private Member follower;

    @ManyToOne
    @JoinColumn(name = "following")
    private Member following;


}
