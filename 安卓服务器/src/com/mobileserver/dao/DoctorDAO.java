package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Doctor;
import com.mobileserver.util.DB;

public class DoctorDAO {

	public List<Doctor> QueryDoctor(String doctorNo,int departmentObj,String name,String education,Timestamp inDate) {
		List<Doctor> doctorList = new ArrayList<Doctor>();
		DB db = new DB();
		String sql = "select * from Doctor where 1=1";
		if (!doctorNo.equals(""))
			sql += " and doctorNo like '%" + doctorNo + "%'";
		if (departmentObj != 0)
			sql += " and departmentObj=" + departmentObj;
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!education.equals(""))
			sql += " and education like '%" + education + "%'";
		if(inDate!=null)
			sql += " and inDate='" + inDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Doctor doctor = new Doctor();
				doctor.setDoctorNo(rs.getString("doctorNo"));
				doctor.setPassword(rs.getString("password"));
				doctor.setDepartmentObj(rs.getInt("departmentObj"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setDoctorPhoto(rs.getString("doctorPhoto"));
				doctor.setEducation(rs.getString("education"));
				doctor.setInDate(rs.getTimestamp("inDate"));
				doctor.setTelephone(rs.getString("telephone"));
				doctor.setVisiteTimes(rs.getInt("visiteTimes"));
				doctor.setMemo(rs.getString("memo"));
				doctorList.add(doctor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return doctorList;
	}
	/* ����ҽ�����󣬽���ҽ�������ҵ�� */
	public String AddDoctor(Doctor doctor) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ҽ�� */
			String sqlString = "insert into Doctor(doctorNo,password,departmentObj,name,sex,doctorPhoto,education,inDate,telephone,visiteTimes,memo) values (";
			sqlString += "'" + doctor.getDoctorNo() + "',";
			sqlString += "'" + doctor.getPassword() + "',";
			sqlString += doctor.getDepartmentObj() + ",";
			sqlString += "'" + doctor.getName() + "',";
			sqlString += "'" + doctor.getSex() + "',";
			sqlString += "'" + doctor.getDoctorPhoto() + "',";
			sqlString += "'" + doctor.getEducation() + "',";
			sqlString += "'" + doctor.getInDate() + "',";
			sqlString += "'" + doctor.getTelephone() + "',";
			sqlString += doctor.getVisiteTimes() + ",";
			sqlString += "'" + doctor.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ҽ����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҽ�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ҽ�� */
	public String DeleteDoctor(String doctorNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Doctor where doctorNo='" + doctorNo + "'";
			db.executeUpdate(sqlString);
			result = "ҽ��ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҽ��ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ҽ����Ż�ȡ��ҽ�� */
	public Doctor GetDoctor(String doctorNo) {
		Doctor doctor = null;
		DB db = new DB();
		String sql = "select * from Doctor where doctorNo='" + doctorNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				doctor = new Doctor();
				doctor.setDoctorNo(rs.getString("doctorNo"));
				doctor.setPassword(rs.getString("password"));
				doctor.setDepartmentObj(rs.getInt("departmentObj"));
				doctor.setName(rs.getString("name"));
				doctor.setSex(rs.getString("sex"));
				doctor.setDoctorPhoto(rs.getString("doctorPhoto"));
				doctor.setEducation(rs.getString("education"));
				doctor.setInDate(rs.getTimestamp("inDate"));
				doctor.setTelephone(rs.getString("telephone"));
				doctor.setVisiteTimes(rs.getInt("visiteTimes"));
				doctor.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return doctor;
	}
	/* ����ҽ�� */
	public String UpdateDoctor(Doctor doctor) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Doctor set ";
			sql += "password='" + doctor.getPassword() + "',";
			sql += "departmentObj=" + doctor.getDepartmentObj() + ",";
			sql += "name='" + doctor.getName() + "',";
			sql += "sex='" + doctor.getSex() + "',";
			sql += "doctorPhoto='" + doctor.getDoctorPhoto() + "',";
			sql += "education='" + doctor.getEducation() + "',";
			sql += "inDate='" + doctor.getInDate() + "',";
			sql += "telephone='" + doctor.getTelephone() + "',";
			sql += "visiteTimes=" + doctor.getVisiteTimes() + ",";
			sql += "memo='" + doctor.getMemo() + "'";
			sql += " where doctorNo='" + doctor.getDoctorNo() + "'";
			db.executeUpdate(sql);
			result = "ҽ�����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ҽ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
