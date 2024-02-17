package com.example.carrrotdiary.global.security;

import com.example.carrrotdiary.global.constants.Role;
import com.example.carrrotdiary.member.dto.MemberDetailDto;
import com.example.carrrotdiary.member.entity.MemberEntity;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final MemberEntity member;

    public UserDetailsImpl(MemberEntity member) {
        this.member = member;
    }

    public static UserDetailsImpl createCustomUserDetails(MemberEntity user) {
        return new UserDetailsImpl(user);
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    public String getEmail() {
        // 유저 이메일로 유저를 구분 (email은 unique 값)
        return member.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(member.getRole().getAuthority());
        return Collections.singleton(authority);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public MemberDetailDto getUserDetailsDto() {
        return new MemberDetailDto(member.getEmail(), member.getNickname(), member.getRole());
    }
}
