package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*ԤԼid*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*ԤԼ�û�*/
    private UserInfo uesrObj;
    public UserInfo getUesrObj() {
        return uesrObj;
    }
    public void setUesrObj(UserInfo uesrObj) {
        this.uesrObj = uesrObj;
    }

    /*ԤԼҽ��*/
    private Doctor doctor;
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /*ԤԼ����*/
    private Timestamp orderDate;
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*ԤԼʱ���*/
    private TimeSlot timeSlotObj;
    public TimeSlot getTimeSlotObj() {
        return timeSlotObj;
    }
    public void setTimeSlotObj(TimeSlot timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }

    /*����״̬*/
    private VisitState visiteStateObj;
    public VisitState getVisiteStateObj() {
        return visiteStateObj;
    }
    public void setVisiteStateObj(VisitState visiteStateObj) {
        this.visiteStateObj = visiteStateObj;
    }

    /*ҽ��˵��*/
    private String introduce;
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

}