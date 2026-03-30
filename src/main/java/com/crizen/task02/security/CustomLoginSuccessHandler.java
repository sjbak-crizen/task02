package com.crizen.task02.security;

import com.crizen.task02.mapper.UserMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 성공 시 자동으로 호출되는 클래스입니다.
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // 인증 성공한 유저 정보를 가져와서, DB의 실패 카운트를 0으로 깔끔하게 초기화해줍니다.
        UserVO user = (UserVO) authentication.getPrincipal();
        userMapper.resetFailCount(user.getUser_id());

        // 부모 클래스의 메서드를 호출하여 원래 가려던 페이지(또는 기본 리스트 화면)로 이동시킵니다.
        super.onAuthenticationSuccess(request, response, authentication);
    }
}