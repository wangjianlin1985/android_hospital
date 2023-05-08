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
	/* 传入科室对象，进行科室的添加业务 */
	public String AddDepartment(Department department) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新科室 */
			String sqlString = "insert into Department(departmentName) values (";
			sqlString += "'" + department.getDepartmentName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "科室添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "科室添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除科室 */
	public String DeleteDepartment(int departmentId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Department where departmentId=" + departmentId;
			db.executeUpdate(sqlString);
			result = "科室删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "科室删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据科室id获取到科室 */
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
	/* 更新科室 */
	public String UpdateDepartment(Department department) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Department set ";
			sql += "departmentName='" + department.getDepartmentName() + "'";
			sql += " where departmentId=" + department.getDepartmentId();
			db.executeUpdate(sql);
			result = "科室更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "科室更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
