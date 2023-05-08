package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Department;
import com.mobileserver.util.DB;

public class DepartmentDAO {

	public List<Department> QueryDepartment() {
		List<Department> departmentList = new ArrayList<Department>();
		DB db = new DB();
		String sql = "select * from Department where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Department department = new Department();
				department.setDepartmentId(rs.getInt("departmentId"));
				department.setDepartmentName(rs.getString("departmentName"));
				departmentList.add(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return departmentList;
	}
	/* ������Ҷ��󣬽��п��ҵ����ҵ�� */
	public String AddDepartment(Department department) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¿��� */
			String sqlString = "insert into Department(departmentName) values (";
			sqlString += "'" + department.getDepartmentName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������ */
	public String DeleteDepartment(int departmentId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Department where departmentId=" + departmentId;
			db.executeUpdate(sqlString);
			result = "����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݿ���id��ȡ������ */
	public Department GetDepartment(int departmentId) {
		Department department = null;
		DB db = new DB();
		String sql = "select * from Department where departmentId=" + departmentId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				department = new Department();
				department.setDepartmentId(rs.getInt("departmentId"));
				department.setDepartmentName(rs.getString("departmentName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return department;
	}
	/* ���¿��� */
	public String UpdateDepartment(Department department) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Department set ";
			sql += "departmentName='" + department.getDepartmentName() + "'";
			sql += " where departmentId=" + department.getDepartmentId();
			db.executeUpdate(sql);
			result = "���Ҹ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ҹ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
