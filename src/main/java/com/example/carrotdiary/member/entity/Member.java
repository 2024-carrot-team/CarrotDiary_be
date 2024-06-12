package com.example.carrotdiary.member.entity;

import com.example.carrotdiary.follow.entity.Follow;
import com.example.carrotdiary.global.common.BaseTimeEntity;
import com.example.carrotdiary.global.constants.Role;
import com.example.carrotdiary.image.entity.Image;
import com.example.carrotdiary.member.dto.MemberRequestDto.updateRequestDto;
import com.example.carrotdiary.post.entity.Post;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String email;

    private String password;
    private String nickname;

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<Follow> followings;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> followers;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime brithDayTime;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;


    @OneToOne(mappedBy = "member")
    private Image image;


    public void updateMember(updateRequestDto updateRequestDto) {
        this.email = updateRequestDto.email();
        this.password = updateRequestDto.password();
        this.nickname = updateRequestDto.nickname();
        this.brithDayTime = updateRequestDto.birthDayTime();
    }

    public void setImageInMemberEntity(Image image) {
        this.image = image;
    }


}