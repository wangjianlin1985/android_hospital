package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Doctor {
    /*医生编号*/
    private String doctorNo;
    public String getDoctorNo() {
        return doctorNo;
    }
    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*所在科室*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*医生照片*/
    private String doctorPhoto;
    public String getDoctorPhoto() {
        return doctorPhoto;
    }
    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    /*学历*/
    private String education;
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }

    /*入院日期*/
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
        this.inDate = inDate;
    }

    /*联系电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*每日出诊次数*/
    private int visiteTimes;
    public int getVisiteTimes() {
        return visiteTimes;
    }
    public void setVisiteTimes(int visiteTimes) {
        this.visiteTimes = visiteTimes;
    }

    /*附加信息*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}