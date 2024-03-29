package com.example.carrotdiary.member.dto;

import com.example.carrotdiary.global.constants.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class MemberDetailDto {
    private String email;
    private String nickName;
    private Role role;


    public MemberDetailDto(String email, String nickName, Role role) {
        this.email = email;
        this.nickName = nickName;
        this.role = role;
    }
}
