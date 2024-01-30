package com.example.carrrotdiary.member.entity;

import com.example.carrrotdiary.global.constants.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class MemberEntity {


    @Id
    @GeneratedValue()
    private long id;

    @Email
    private String email;

    private String password;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime brithDayTime;

    private Role role;

    @Builder
    public MemberEntity(long id, String email, String password, String nickname, LocalDateTime brithDayTime, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.brithDayTime = brithDayTime;
        this.role = role;
    }


}
