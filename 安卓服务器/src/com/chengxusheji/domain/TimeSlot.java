package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TimeSlot {
    /*ʱ���id*/
    private int timeSlotId;
    public int getTimeSlotId() {
        return timeSlotId;
    }
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    /*ʱ�������*/
    private String timeSlotName;
    public String getTimeSlotName() {
        return timeSlotName;
    }
    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

}