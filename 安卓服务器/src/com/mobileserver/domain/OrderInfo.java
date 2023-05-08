package com.mobileserver.domain;

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
    private String uesrObj;
    public String getUesrObj() {
        return uesrObj;
    }
    public void setUesrObj(String uesrObj) {
        this.uesrObj = uesrObj;
    }

    /*预约医生*/
    private String doctor;
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    /*预约日期*/
    private java.sql.Timestamp orderDate;
    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*预约时间段*/
    private int timeSlotObj;
    public int getTimeSlotObj() {
        return timeSlotObj;
    }
    public void setTimeSlotObj(int timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }

    /*出诊状态*/
    private int visiteStateObj;
    public int getVisiteStateObj() {
        return visiteStateObj;
    }
    public void setVisiteStateObj(int visiteStateObj) {
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