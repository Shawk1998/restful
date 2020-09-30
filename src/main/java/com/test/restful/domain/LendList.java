package com.test.restful.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LendList {
    private Long sernum;

    private Long bookId;

    private Integer readerId;

    private Date lendDate;

    private Date backDate;

    public Long getSernum() {
        return sernum;
    }

    public void setSernum(Long sernum) {
        this.sernum = sernum;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getReaderId() {
        return readerId;
    }

    public void setReaderId(Integer readerId) {
        this.readerId = readerId;
    }

    public String getLendDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(lendDate);
    }

    public void setLendDate(String lendDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            java.util.Date date = sdf.parse(lendDate);
            this.lendDate = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getBackDate() {
        return backDate;
    }

    public void setBackDate(Date backDate) {
        this.backDate = backDate;
    }
}