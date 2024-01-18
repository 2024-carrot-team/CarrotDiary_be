package com.example.carrrotdiary.member.dto;

import com.example.carrrotdiary.global.constants.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class MemberRequestDto {


    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    private String nickname;
    private LocalDateTime brithDayTime;

    private Role role;


    @Getter
    @NoArgsConstructor
    public static class loginRequestDto {
        private String email;

        private String password;
        public loginRequestDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
