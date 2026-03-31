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

// CustomAuthenticationProvider에서 로그인 실패로 인해 예외(Exception)가 던져지면,
// 스프링 시큐리티가 자동으로 이 클래스를 호출하여 실패 후속 조치를 취하게 됩니다.
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 실패 횟수를 DB에 업데이트하거나 사용자 상태를 조회하기 위해 매퍼를 주입받습니다.
    @Autowired
    private UserMapper userMapper;

    // 로그인 실패 시 자동으로 실행되는 핵심 메서드입니다.
    // 매개변수인 exception 안에 검문소에서 우리가 작성했던 에러 메시지("비밀번호가 일치하지 않습니다." 등)가 담겨서 넘어옵니다.
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 사용자가 로그인 폼(login.jsp)에서 입력하고 전송했던 아이디(input태그의 name값)를 꺼내옵니다.
        String userId = request.getParameter("user_id");

        // DB에서 해당 아이디를 가진 유저가 실제로 존재하는지, 현재 잠금 상태는 어떤지 조회합니다.
        UserVO user = userMapper.getUserById(userId);

        // 검문소(Provider)에서 던진 예외 메시지를 그대로 꺼내옵니다.
        String errorMessage = exception.getMessage();

        // 사용자가 존재하고, 예외가 '비밀번호 불일치(BadCredentialsException)'일 때만 카운트를 올립니다.
        // "존재하지 않는 아이디입니다" 같은 에러일 때는 허공에 카운트를 올릴 수 없으므로 걸러내는 방어 로직입니다.
        if (user != null && exception instanceof BadCredentialsException) {

            // 이미 잠긴 계정("Y")이라면 더 이상 카운트를 올리거나 계산할 필요가 없으므로 "N"일 때만 로직을 탑니다.
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
        // 세션이 아닌 request에 담았기 때문에, 화면이 한 번 새로고침(또는 이동) 되면 메시지는 깔끔하게 사라집니다(1회성).
        request.setAttribute("errorMessage", errorMessage);

        // redirect가 아닌 forward 방식을 사용합니다.
        // redirect를 쓰면 방금 request에 담은 errorMessage 데이터가 싹 날아가 버리지만,
        // forward를 쓰면 데이터를 안전하게 품고 컨트롤러의 '/login' 주소로 이동할 수 있습니다.
        request.getRequestDispatcher("/login?error=true").forward(request, response);
    }
}