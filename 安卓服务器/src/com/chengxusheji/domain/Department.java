package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Department {
    /*¿ÆÊÒid*/
    private int departmentId;
    public int getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /*¿ÆÊÒÃû³Æ*/
    private String departmentName;
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}