package com.crizen.task02.vo;

import java.util.Date;

public class UserVO {
    private String user_id;     // 아이디
    private String password;    // 비밀번호
    private String user_name;   // 이름
    private String role;        // 권한 (ROLE_USER, ROLE_ADMIN)
    private int fail_cnt;       // 틀린 횟수
    private String is_locked;   // 잠금 여부 (Y/N)
    private String allowed_ip;  // 허용 IP
    private Date reg_date;      // 가입일

    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUser_name() { return user_name; }
    public void setUser_name(String user_name) { this.user_name = user_name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public int getFail_cnt() { return fail_cnt; }
    public void setFail_cnt(int fail_cnt) { this.fail_cnt = fail_cnt; }
    public String getIs_locked() { return is_locked; }
    public void setIs_locked(String is_locked) { this.is_locked = is_locked; }
    public String getAllowed_ip() { return allowed_ip; }
    public void setAllowed_ip(String allowed_ip) { this.allowed_ip = allowed_ip; }
    public Date getReg_date() { return reg_date; }
    public void setReg_date(Date reg_date) { this.reg_date = reg_date; }
}