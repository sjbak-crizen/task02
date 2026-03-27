package com.crizen.task02.vo;

import java.util.Date;

public class CounselVO {
    private int seq_counsel;
    private String counsel_title;
    private String counsel_content;
    private String counsel_writer;
    private Date counsel_date;

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
}
