package com.example.carrotdiary.global.security;

import com.example.carrotdiary.member.dto.MemberDetailDto;
import com.example.carrotdiary.member.entity.Member;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public static UserDetailsImpl createCustomUserDetails(Member user) {
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
