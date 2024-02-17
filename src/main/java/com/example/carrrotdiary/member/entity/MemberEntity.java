package com.example.carrrotdiary.member.entity;

import com.example.carrrotdiary.global.constants.Role;
import com.example.carrrotdiary.member.dto.MemberRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;

    private String password;
    private String nickname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime brithDayTime;
    @Enumerated(EnumType.STRING)
    private Role role;


    public void updateMember(MemberRequestDto.updateRequestDto updateRequestDto) {
        this.email = updateRequestDto.email();
        this.password = updateRequestDto.password();
        this.nickname = updateRequestDto.nickname();
        this.brithDayTime = updateRequestDto.birthDayTime();
    }

}
