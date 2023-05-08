package com.mobileclient.domain;

import java.io.Serializable;

public class LeaveWord implements Serializable {
    /*留言id*/
    private int leaveWordId;
    public int getLeaveWordId() {
        return leaveWordId;
    }
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }

    /*标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*留言内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*留言时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*留言人*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*回复内容*/
    private String replyContent;
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    /*回复时间*/
    private String replyTime;
    public String getReplyTime() {
        return replyTime;
    }
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    /*回复的医生*/
    private String replyDoctor;
    public String getReplyDoctor() {
        return replyDoctor;
    }
    public void setReplyDoctor(String replyDoctor) {
        this.replyDoctor = replyDoctor;
    }

}