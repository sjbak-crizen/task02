package com.crizen.task02.security;

import com.crizen.task02.mapper.UserMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 로그인 실패 시 자동으로 호출되는 클래스입니다.
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired private UserMapper userMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String userId = request.getParameter("user_id");
        UserVO user = userMapper.getUserById(userId);

        String errorMessage = exception.getMessage();

        // 사용자가 존재하고, 예외가 '비밀번호 불일치(BadCredentialsException)'일 때만 카운트를 올립니다.
        if (user != null && exception instanceof BadCredentialsException) {
            if (!"Y".equals(user.getIs_locked())) {
                userMapper.updateFailCount(userId); // DB 카운트 +1
                UserVO updatedUser = userMapper.getUserById(userId); // 업데이트된 정보 다시 조회

                // 5회 이상 틀렸으면 계정 잠금 처리
                if (updatedUser.getFail_cnt() >= 5) {
                    userMapper.lockUser(userId);
                    errorMessage = "비밀번호 5회 오류로 계정이 잠겼습니다. 관리자에게 문의하세요.";
                } else {
                    errorMessage = "비밀번호가 틀렸습니다. (남은 횟수: " + (5 - updatedUser.getFail_cnt()) + "회)";
                }
            }
        }

        // 화면에 에러 메시지를 띄워주기 위해 request에 담아서 로그인 페이지로 돌려보냅니다.
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/login?error=true").forward(request, response);
    }
}