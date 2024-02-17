package com.example.carrrotdiary.global.jwt;

import com.example.carrrotdiary.global.constants.Role;
import com.example.carrrotdiary.global.security.UserDetailsImpl;
import com.example.carrrotdiary.member.dto.MemberDetailDto;
import com.example.carrrotdiary.member.dto.MemberRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtUtils jwtUtil;

    public JwtAuthenticationFilter(JwtUtils jwtUtil) {
        this.jwtUtil = jwtUtil;
        // 로그인 URL 설정
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 데이터를 MemberRequestDto.LoginRequestDto 객체로 역직렬화
            MemberRequestDto.LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getInputStream(), MemberRequestDto.LoginRequestDto.class);
            // UsernamePasswordAuthenticationToken 생성
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequestDto.email(),
                    loginRequestDto.password(),
                    null
            );

            // AuthenticationManager를 사용하여 인증 시도
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("JSON parsing error during authentication", e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("로그인 성공 및 JWT 생성");
        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        MemberDetailDto userDetailsDto = userDetails.getUserDetailsDto();

        String email = userDetailsDto.getEmail();
        String username = userDetailsDto.getNickName();
        Role role = userDetailsDto.getRole();

        String token = jwtUtil.createToken(email, role);
        jwtUtil.addJwtToCookie(token, response);

        response.getWriter().write("login success");
//        String queryString = request.getQueryString();
//        String baseURL = "/";
//        if (queryString != null && queryString.contains("=")) {
//            baseURL = queryString.substring(queryString.indexOf('=') + 1);
//        }
//        response.sendRedirect(baseURL);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        logger.error("로그인 실패: " + failed.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}