package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LeaveWord;
import com.mobileserver.util.DB;

public class LeaveWordDAO {

	public List<LeaveWord> QueryLeaveWord(String title,String userObj,String replyTime,String replyDoctor) {
		List<LeaveWord> leaveWordList = new ArrayList<LeaveWord>();
		DB db = new DB();
		String sql = "select * from LeaveWord where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!replyTime.equals(""))
			sql += " and replyTime like '%" + replyTime + "%'";
		if (!replyDoctor.equals(""))
			sql += " and replyDoctor like '%" + replyDoctor + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LeaveWord leaveWord = new LeaveWord();
				leaveWord.setLeaveWordId(rs.getInt("leaveWordId"));
				leaveWord.setTitle(rs.getString("title"));
				leaveWord.setContent(rs.getString("content"));
				leaveWord.setAddTime(rs.getString("addTime"));
				leaveWord.setUserObj(rs.getString("userObj"));
				leaveWord.setReplyContent(rs.getString("replyContent"));
				leaveWord.setReplyTime(rs.getString("replyTime"));
				leaveWord.setReplyDoctor(rs.getString("replyDoctor"));
				leaveWordList.add(leaveWord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveWordList;
	}
	/* �������Զ��󣬽������Ե����ҵ�� */
	public String AddLeaveWord(LeaveWord leaveWord) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в��������� */
			String sqlString = "insert into LeaveWord(title,content,addTime,userObj,replyContent,replyTime,replyDoctor) values (";
			sqlString += "'" + leaveWord.getTitle() + "',";
			sqlString += "'" + leaveWord.getContent() + "',";
			sqlString += "'" + leaveWord.getAddTime() + "',";
			sqlString += "'" + leaveWord.getUserObj() + "',";
			sqlString += "'" + leaveWord.getReplyContent() + "',";
			sqlString += "'" + leaveWord.getReplyTime() + "',";
			sqlString += "'" + leaveWord.getReplyDoctor() + "'";
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
	public String DeleteLeaveWord(int leaveWordId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveWord where leaveWordId=" + leaveWordId;
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

	/* ��������id��ȡ������ */
	public LeaveWord GetLeaveWord(int leaveWordId) {
		LeaveWord leaveWord = null;
		DB db = new DB();
		String sql = "select * from LeaveWord where leaveWordId=" + leaveWordId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				leaveWord = new LeaveWord();
				leaveWord.setLeaveWordId(rs.getInt("leaveWordId"));
				leaveWord.setTitle(rs.getString("title"));
				leaveWord.setContent(rs.getString("content"));
				leaveWord.setAddTime(rs.getString("addTime"));
				leaveWord.setUserObj(rs.getString("userObj"));
				leaveWord.setReplyContent(rs.getString("replyContent"));
				leaveWord.setReplyTime(rs.getString("replyTime"));
				leaveWord.setReplyDoctor(rs.getString("replyDoctor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveWord;
	}
	/* �������� */
	public String UpdateLeaveWord(LeaveWord leaveWord) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveWord set ";
			sql += "title='" + leaveWord.getTitle() + "',";
			sql += "content='" + leaveWord.getContent() + "',";
			sql += "addTime='" + leaveWord.getAddTime() + "',";
			sql += "userObj='" + leaveWord.getUserObj() + "',";
			sql += "replyContent='" + leaveWord.getReplyContent() + "',";
			sql += "replyTime='" + leaveWord.getReplyTime() + "',";
			sql += "replyDoctor='" + leaveWord.getReplyDoctor() + "'";
			sql += " where leaveWordId=" + leaveWord.getLeaveWordId();
			db.executeUpdate(sql);
			result = "���Ը��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���Ը���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
