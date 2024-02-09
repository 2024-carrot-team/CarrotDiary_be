package com.example.carrrotdiary.member.entity;

import com.example.carrrotdiary.global.constants.Role;
import com.example.carrrotdiary.member.dto.MemberRequestDto;
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
@Builder
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


    public MemberEntity(long id, String email, String password, String nickname, LocalDateTime brithDayTime, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.brithDayTime = brithDayTime;
        this.role = role;
    }


    public void updateMember(MemberRequestDto.updateRequestDto updateRequestDto) {
        this.email = updateRequestDto.getEmail();
        this.password = updateRequestDto.getPassword();
        this.nickname = updateRequestDto.getNickname();
        this.brithDayTime = updateRequestDto.getBrithDayTime();
    }

}
