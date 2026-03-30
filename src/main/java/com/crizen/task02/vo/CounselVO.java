package com.crizen.task02.vo;

import java.util.Date;

public class CounselVO {
    private int seq_counsel;
    private String counsel_title;
    private String counsel_content;
    private String counsel_writer;
    private Date counsel_date;
    private String modify_id;
    private Date modify_date;
    private String searchType;    // 검색 조건 (writer, date, title, title_content)
    private String searchKeyword; // 사용자가 입력한 검색어

    public int getSeq_counsel() {
        return seq_counsel;
    }

    public void setSeq_counsel(int seq_counsel) {
        this.seq_counsel = seq_counsel;
    }

    public String getCounsel_title() {
        return counsel_title;
    }

    public void setCounsel_title(String counsel_title) {
        this.counsel_title = counsel_title;
    }

    public String getCounsel_content() {
        return counsel_content;
    }

    public void setCounsel_content(String counsel_content) {
        this.counsel_content = counsel_content;
    }

    public String getCounsel_writer() {
        return counsel_writer;
    }

    public void setCounsel_writer(String counsel_writer) {
        this.counsel_writer = counsel_writer;
    }

    public Date getCounsel_date() {
        return counsel_date;
    }

    public void setCounsel_date(Date counsel_date) {
        this.counsel_date = counsel_date;
    }

    public String getModify_id() {
        return modify_id;
    }

    public void setModify_id(String modify_id) {
        this.modify_id = modify_id;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}
