package com.mobileserver.domain;

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
    private String uesrObj;
    public String getUesrObj() {
        return uesrObj;
    }
    public void setUesrObj(String uesrObj) {
        this.uesrObj = uesrObj;
    }

    /*ԤԼҽ��*/
    private String doctor;
    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    /*ԤԼ����*/
    private java.sql.Timestamp orderDate;
    public java.sql.Timestamp getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(java.sql.Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    /*ԤԼʱ���*/
    private int timeSlotObj;
    public int getTimeSlotObj() {
        return timeSlotObj;
    }
    public void setTimeSlotObj(int timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }

    /*����״̬*/
    private int visiteStateObj;
    public int getVisiteStateObj() {
        return visiteStateObj;
    }
    public void setVisiteStateObj(int visiteStateObj) {
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