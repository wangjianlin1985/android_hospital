package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Doctor {
    /*ҽ�����*/
    private String doctorNo;
    public String getDoctorNo() {
        return doctorNo;
    }
    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*���ڿ���*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*ҽ����Ƭ*/
    private String doctorPhoto;
    public String getDoctorPhoto() {
        return doctorPhoto;
    }
    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    /*ѧ��*/
    private String education;
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }

    /*��Ժ����*/
    private Timestamp inDate;
    public Timestamp getInDate() {
        return inDate;
    }
    public void setInDate(Timestamp inDate) {
        this.inDate = inDate;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*ÿ�ճ������*/
    private int visiteTimes;
    public int getVisiteTimes() {
        return visiteTimes;
    }
    public void setVisiteTimes(int visiteTimes) {
        this.visiteTimes = visiteTimes;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

}