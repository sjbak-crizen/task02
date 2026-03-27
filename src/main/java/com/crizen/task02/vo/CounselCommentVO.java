package com.crizen.task02.vo;

import java.util.Date;

public class CounselCommentVO {
    private int seq_comment;
    private int seq_counsel;
    private String comment_content;
    private String comment_writer;
    private Date comment_date;

    public int getSeq_comment() {
        return seq_comment;
    }

    public void setSeq_comment(int seq_comment) {
        this.seq_comment = seq_comment;
    }

    public int getSeq_counsel() {
        return seq_counsel;
    }

    public void setSeq_counsel(int seq_counsel) {
        this.seq_counsel = seq_counsel;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_writer() {
        return comment_writer;
    }

    public void setComment_writer(String comment_writer) {
        this.comment_writer = comment_writer;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }
}