package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.UserInfo;
import com.mobileserver.util.DB;

public class UserInfoDAO {

	public List<UserInfo> QueryUserInfo(String user_name,String name,Timestamp birthday,String jiguan) {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		DB db = new DB();
		String sql = "select * from UserInfo where 1=1";
		if (!user_name.equals(""))
			sql += " and user_name like '%" + user_name + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(birthday!=null)
			sql += " and birthday='" + birthday + "'";
		if (!jiguan.equals(""))
			sql += " and jiguan like '%" + jiguan + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setName(rs.getString("name"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setJiguan(rs.getString("jiguan"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setAddress(rs.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfoList;
	}
	/* �����û����󣬽����û������ҵ�� */
	public String AddUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������û� */
			String sqlString = "insert into UserInfo(user_name,password,name,sex,userPhoto,birthday,jiguan,telephone,address) values (";
			sqlString += "'" + userInfo.getUser_name() + "',";
			sqlString += "'" + userInfo.getPassword() + "',";
			sqlString += "'" + userInfo.getName() + "',";
			sqlString += "'" + userInfo.getSex() + "',";
			sqlString += "'" + userInfo.getUserPhoto() + "',";
			sqlString += "'" + userInfo.getBirthday() + "',";
			sqlString += "'" + userInfo.getJiguan() + "',";
			sqlString += "'" + userInfo.getTelephone() + "',";
			sqlString += "'" + userInfo.getAddress() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�û���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���û� */
	public String DeleteUserInfo(String user_name) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from UserInfo where user_name='" + user_name + "'";
			db.executeUpdate(sqlString);
			result = "�û�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �����û�����ȡ���û� */
	public UserInfo GetUserInfo(String user_name) {
		UserInfo userInfo = null;
		DB db = new DB();
		String sql = "select * from UserInfo where user_name='" + user_name + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				userInfo = new UserInfo();
				userInfo.setUser_name(rs.getString("user_name"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setName(rs.getString("name"));
				userInfo.setSex(rs.getString("sex"));
				userInfo.setUserPhoto(rs.getString("userPhoto"));
				userInfo.setBirthday(rs.getTimestamp("birthday"));
				userInfo.setJiguan(rs.getString("jiguan"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return userInfo;
	}
	/* �����û� */
	public String UpdateUserInfo(UserInfo userInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update UserInfo set ";
			sql += "password='" + userInfo.getPassword() + "',";
			sql += "name='" + userInfo.getName() + "',";
			sql += "sex='" + userInfo.getSex() + "',";
			sql += "userPhoto='" + userInfo.getUserPhoto() + "',";
			sql += "birthday='" + userInfo.getBirthday() + "',";
			sql += "jiguan='" + userInfo.getJiguan() + "',";
			sql += "telephone='" + userInfo.getTelephone() + "',";
			sql += "address='" + userInfo.getAddress() + "'";
			sql += " where user_name='" + userInfo.getUser_name() + "'";
			db.executeUpdate(sql);
			result = "�û����³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�û�����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
