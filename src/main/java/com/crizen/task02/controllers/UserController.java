package com.crizen.task02.controllers;

import com.crizen.task02.mapper.UserMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // security-context.xml에서 등록한 BCrypt 암호화 기계를 가져옵니다.
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 1. 커스텀 로그인 화면 띄우기
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(HttpServletRequest request, Model model) {
        // CustomLoginFailureHandler에서 request에 담아 보낸 "errorMessage"를 꺼내서 화면에 넘겨줍니다.
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        return "user/login"; // /WEB-INF/views/user/login.jsp 로 이동
    }

    // 2. 회원가입 화면 띄우기
    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String registerForm() {
        return "user/register"; // /WEB-INF/views/user/register.jsp 로 이동
    }

    // 3. 회원가입 실제 처리 (비밀번호 암호화 포함!)
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String registerAction(UserVO vo) {
        // 사용자가 입력한 평문 비밀번호를 BCrypt로 암호화("$2a$10$X...")합니다.
        String encodedPassword = passwordEncoder.encode(vo.getPassword());

        // 암호화된 비밀번호를 다시 VO에 세팅합니다.
        vo.setPassword(encodedPassword);

        // 안전하게 암호화된 상태로 DB에 저장합니다.
        userMapper.insertUser(vo);

        // 가입이 완료되면 다시 로그인 화면으로 돌려보냅니다.
        return "redirect:/login";
    }

    // 현재 로그인한 유저 정보를 가져오는 공통 메서드
    private UserVO getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserVO) auth.getPrincipal();
    }

    // 4. 비밀번호 변경 화면 띄우기 (GET)
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String changePasswordForm() {
        return "user/changePassword"; // /WEB-INF/views/user/changePassword.jsp
    }

    // 5. 비밀번호 변경 실제 처리 (POST)
    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public String changePasswordAction(@RequestParam("currentPassword") String currentPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       RedirectAttributes rttr) {

        // 1. 세션에서 현재 로그인한 사용자 정보(ID)를 가져온 뒤, DB에서 최신 암호화 비밀번호를 다시 조회합니다.
        String userId = getLoginUser().getUser_id();
        UserVO dbUser = userMapper.getUserById(userId);

        // 2. 사용자가 입력한 '현재 비밀번호(평문)'와 DB의 '암호화된 비밀번호' 비교
        if (!passwordEncoder.matches(currentPassword, dbUser.getPassword())) {
            // 비밀번호가 틀리면 에러 메시지와 함께 다시 변경 화면으로 쫓아냅니다.
            rttr.addFlashAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
            return "redirect:/user/changePassword";
        }

        // 3. 검증을 통과했다면, '새로운 비밀번호'를 BCrypt로 안전하게 암호화합니다.
        String encodedNewPassword = passwordEncoder.encode(newPassword);

        // 4. DB 업데이트를 위해 VO에 세팅하고 Mapper 호출
        UserVO updateVO = new UserVO();
        updateVO.setUser_id(userId);
        updateVO.setPassword(encodedNewPassword);

        userMapper.updatePassword(updateVO);

        // 5. 비밀번호가 변경되었으므로 기존 세션을 만료시키기 위해 강제 로그아웃 처리합니다.
        // security-context.xml의 로그아웃 URL(/logout)을 호출하여 안전하게 밖으로 내보냅니다.
        return "redirect:/logout";
    }
}