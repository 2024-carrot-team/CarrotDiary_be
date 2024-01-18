package com.example.carrrotdiary.member.entity;

import com.example.carrrotdiary.global.constants.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
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
}
