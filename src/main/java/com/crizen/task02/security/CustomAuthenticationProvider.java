package com.crizen.task02.security;

import com.crizen.task02.mapper.UserMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.ArrayList;
import java.util.List;

// AuthenticationProvider: 사용자가 입력한 아이디/비번을 DB와 비교하여 실제 인증을 수행하는 핵심 검문소입니다.
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 사용자가 입력한 아이디와 비밀번호를 꺼냅니다.
        String userId = authentication.getName();
        String password = (String) authentication.getCredentials();

        // 2. DB에서 유저 정보를 조회합니다.
        UserVO user = userMapper.getUserById(userId);

        // 3. 아이디 검증
        if (user == null) {
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        // 4. 계정 잠금 여부 검증 (5회 틀렸는지)
        if ("Y".equals(user.getIs_locked())) {
            throw new LockedException("비밀번호 5회 오류로 계정이 잠겼습니다.");
        }

        // 5. 허용 IP 검증 (요구사항)
        // WebAuthenticationDetails 객체를 통해 접속자의 실제 IP를 추출합니다.
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteIp = details.getRemoteAddress();

        if (user.getAllowed_ip() != null && !user.getAllowed_ip().isEmpty()) {
            // 로컬 테스트 시 IPv6(0:0:0:0:0:0:0:1)로 잡히는 것을 IPv4(127.0.0.1)로 보정
            String checkIp = remoteIp.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : remoteIp;
            if (!user.getAllowed_ip().equals(checkIp)) {
                throw new BadCredentialsException("접근이 허용되지 않은 IP입니다.");
            }
        }

        // 6. 비밀번호 검증 (BCrypt 암호화 비교)
        // passwordEncoder.matches(평문, 암호화된문자열)를 사용하여 비교합니다.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        // 7. 모든 검증 통과! 권한(Role)을 부여하고 인증 성공 토큰을 발급합니다.
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}