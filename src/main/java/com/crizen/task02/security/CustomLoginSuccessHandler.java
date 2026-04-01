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
// 단순한 SuccessHandler가 아니라 'SavedRequestAware(저장된 요청을 기억하는)' 핸들러를 상속받았습니다.
// 사용자가 로그인이 필요한 페이지(예: /counsel/write)에 접속하려다 로그인 창으로 튕겼을 때,
// 로그인 성공 후 원래 가려던 그 페이지(/counsel/write)로 돌려보내주는 강력한 부모 클래스입니다.
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    // 로그인에 성공했으니, DB에 기록된 '실패 횟수'를 0으로 만들어주기 위해 Mapper를 주입받습니다.
    @Autowired private UserMapper userMapper;

    // CustomAuthenticationProvider(검문소)에서 인증이 완벽하게 통과되면 이 메서드가 실행됩니다.
    // 매개변수인 authentication 안에는 검문소에서 최종적으로 만들어준 '인증 성공 토큰'이 들어있습니다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        // 인증 성공한 유저 정보를 가져와서, DB의 실패 카운트를 0으로 깔끔하게 초기화해줍니다.
        // authentication.getPrincipal()은 검문소(Provider)의 7번 단계에서 우리가 토큰에 담아 보냈던 바로 그 UserVO 객체입니다!
        // Object 타입으로 리턴되므로 (UserVO)로 형변환(캐스팅)하여 꺼냅니다.
        UserVO user = (UserVO) authentication.getPrincipal();

        // 사용자가 이전에 비밀번호를 1~4번 틀렸더라도, 이번에 성공했으므로 0으로 초기화(Reset)합니다.
        userMapper.resetFailCount(user.getUser_id());

        // 부모 클래스의 메서드를 호출하여 원래 가려던 페이지(또는 기본 리스트 화면)로 이동시킵니다.
        // 우리가 직접 response.sendRedirect()를 쓰지 않고 부모 메서드(super)에 처리를 맡기는 이유입니다.
        // 부모 클래스가 세션에 저장된 '사용자가 원래 가고 싶어 했던 주소'를 찾아내서 알아서 그곳으로 안내해 줍니다.
        super.onAuthenticationSuccess(request, response, authentication);
    }
}