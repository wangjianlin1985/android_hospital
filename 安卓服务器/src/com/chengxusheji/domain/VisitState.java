package com.chengxusheji.domain;

import java.sql.Timestamp;
public class VisitState {
    /*״̬id*/
    private int visitStateId;
    public int getVisitStateId() {
        return visitStateId;
    }
    public void setVisitStateId(int visitStateId) {
        this.visitStateId = visitStateId;
    }

    /*����״̬*/
    private String visitStateName;
    public String getVisitStateName() {
        return visitStateName;
    }
    public void setVisitStateName(String visitStateName) {
        this.visitStateName = visitStateName;
    }

}