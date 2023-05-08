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
	/* 传入时间段对象，进行时间段的添加业务 */
	public String AddTimeSlot(TimeSlot timeSlot) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新时间段 */
			String sqlString = "insert into TimeSlot(timeSlotName) values (";
			sqlString += "'" + timeSlot.getTimeSlotName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "时间段添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除时间段 */
	public String DeleteTimeSlot(int timeSlotId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from TimeSlot where timeSlotId=" + timeSlotId;
			db.executeUpdate(sqlString);
			result = "时间段删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据时间段id获取到时间段 */
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
	/* 更新时间段 */
	public String UpdateTimeSlot(TimeSlot timeSlot) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update TimeSlot set ";
			sql += "timeSlotName='" + timeSlot.getTimeSlotName() + "'";
			sql += " where timeSlotId=" + timeSlot.getTimeSlotId();
			db.executeUpdate(sql);
			result = "时间段更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "时间段更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
