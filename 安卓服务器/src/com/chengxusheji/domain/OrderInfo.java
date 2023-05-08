package com.chengxusheji.domain;

import java.sql.Timestamp;
public class OrderInfo {
    /*预约id*/
    private int orderId;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /*预约用户*/
    private UserInfo uesrObj;
    public UserInfo getUesrObj() {
        return uesrObj;
    }
    public void setUesrObj(UserInfo uesrObj) {
        this.uesrObj = uesrObj;
    }

    /*预约医生*/
    private Doctor doctor;
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    /*预约日期*/
    private Timestamp orderDate;
    public Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*预约时间段*/
    private TimeSlot timeSlotObj;
    public TimeSlot getTimeSlotObj() {
        return timeSlotObj;
    }
    public void setTimeSlotObj(TimeSlot timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }

    /*出诊状态*/
    private VisitState visiteStateObj;
    public VisitState getVisiteStateObj() {
        return visiteStateObj;
    }
    public void setVisiteStateObj(VisitState visiteStateObj) {
        this.visiteStateObj = visiteStateObj;
    }

    /*医生说明*/
    private String introduce;
    public String getIntroduce() {
        return introduce;
    }
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

}