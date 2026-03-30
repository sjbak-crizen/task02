package com.crizen.task02.mapper;

import com.crizen.task02.vo.UserVO;

public interface UserMapper {
    // 1. 아이디로 유저 정보 가져오기
    UserVO getUserById(String user_id);

    // 2. 로그인 실패 시 카운트 1 증가
    void updateFailCount(String user_id);

    // 3. 실패 5회 도달 시 계정 잠금 (is_locked = 'Y')
    void lockUser(String user_id);

    // 4. 로그인 성공 시 실패 카운트 초기화 (0으로)
    void resetFailCount(String user_id);

    // 5. 회원가입
    void insertUser(UserVO vo);
}