package com.crizen.task02.controllers;

import com.crizen.task02.mapper.UserMapper;
import com.crizen.task02.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        // [핵심!] 사용자가 입력한 평문 비밀번호("admin123!")를 BCrypt로 암호화("$2a$10$X...")합니다.
        String encodedPassword = passwordEncoder.encode(vo.getPassword());

        // 암호화된 비밀번호를 다시 VO에 세팅합니다.
        vo.setPassword(encodedPassword);

        // 안전하게 암호화된 상태로 DB에 저장합니다.
        userMapper.insertUser(vo);

        // 가입이 완료되면 다시 로그인 화면으로 돌려보냅니다.
        return "redirect:/login";
    }
}