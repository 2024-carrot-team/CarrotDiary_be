package com.example.carrotdiary.member.dto;

import com.example.carrotdiary.global.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDateTime;

public record MemberRequestDto(

        @Email
        String email,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
                message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        String password,
        String nickname,
        LocalDateTime birthDayTime,
        Role role,
        String adminToken
//        String imageUrl
) {

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    @Builder
    public record LoginRequestDto(
            String email,
            String password
    ) {}

    public record updateRequestDto(
            String email,
            String password,
            String nickname,
//            String imageUrl,
            LocalDateTime birthDayTime
    ) {}

}