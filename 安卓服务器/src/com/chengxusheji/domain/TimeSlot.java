package com.chengxusheji.domain;

import java.sql.Timestamp;
public class TimeSlot {
    /*时间段id*/
    private int timeSlotId;
    public int getTimeSlotId() {
        return timeSlotId;
    }
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    /*时间段名称*/
    private String timeSlotName;
    public String getTimeSlotName() {
        return timeSlotName;
    }
    public void setTimeSlotName(String timeSlotName) {
        this.timeSlotName = timeSlotName;
    }

}