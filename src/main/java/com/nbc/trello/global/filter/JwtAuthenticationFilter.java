package com.nbc.trello.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbc.trello.entity.user.User;
import com.nbc.trello.global.dto.request.LoginRequestDto;
import com.nbc.trello.global.util.JwtUtil;
import com.nbc.trello.global.util.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 로그인 시도 : JSON 데이터를 LoginRequestDto 객체로 변환
            // request.getInputStream() : 요청으로부터 받은 데이터를 읽어옴
            // ObjectMapper : JSON 형식의 데이터를 LoginRequestDto 객체로 매핑
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            log.info("로그인 시도");

            // getAuthenticationManager() : Spring Security 에서 인증을 처리하는데 사용되는 인증 관리자(객체)
            // authenticate() : 사용자의 인증을 시도
            // UsernamePasswordAuthenticationToken : 사용자의 인증 정보(이메일, 비밀번호)를 인증 매니저에 전달
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();
        User user = userDetailsImpl.getUser();

        String token = jwtUtil.createToken(user);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        log.info("로그인 성공 및 JWT 생성");
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("로그인 실패");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}