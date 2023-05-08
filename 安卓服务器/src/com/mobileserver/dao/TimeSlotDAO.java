package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.TimeSlot;
import com.mobileserver.util.DB;

public class TimeSlotDAO {

	public List<TimeSlot> QueryTimeSlot() {
		List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		DB db = new DB();
		String sql = "select * from TimeSlot where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setTimeSlotId(rs.getInt("timeSlotId"));
				timeSlot.setTimeSlotName(rs.getString("timeSlotName"));
				timeSlotList.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return timeSlotList;
	}
	/* ����ʱ��ζ��󣬽���ʱ��ε����ҵ�� */
	public String AddTimeSlot(TimeSlot timeSlot) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ʱ��� */
			String sqlString = "insert into TimeSlot(timeSlotName) values (";
			sqlString += "'" + timeSlot.getTimeSlotName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ʱ�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ʱ��� */
	public String DeleteTimeSlot(int timeSlotId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TimeSlot where timeSlotId=" + timeSlotId;
			db.executeUpdate(sqlString);
			result = "ʱ���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ʱ���id��ȡ��ʱ��� */
	public TimeSlot GetTimeSlot(int timeSlotId) {
		TimeSlot timeSlot = null;
		DB db = new DB();
		String sql = "select * from TimeSlot where timeSlotId=" + timeSlotId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				timeSlot = new TimeSlot();
				timeSlot.setTimeSlotId(rs.getInt("timeSlotId"));
				timeSlot.setTimeSlotName(rs.getString("timeSlotName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return timeSlot;
	}
	/* ����ʱ��� */
	public String UpdateTimeSlot(TimeSlot timeSlot) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TimeSlot set ";
			sql += "timeSlotName='" + timeSlot.getTimeSlotName() + "'";
			sql += " where timeSlotId=" + timeSlot.getTimeSlotId();
			db.executeUpdate(sql);
			result = "ʱ��θ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ʱ��θ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
