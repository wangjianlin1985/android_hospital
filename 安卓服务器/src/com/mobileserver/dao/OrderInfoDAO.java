package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	public List<OrderInfo> QueryOrderInfo(String uesrObj,String doctor,Timestamp orderDate,int timeSlotObj,int visiteStateObj) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!uesrObj.equals(""))
			sql += " and uesrObj = '" + uesrObj + "'";
		if (!doctor.equals(""))
			sql += " and doctor = '" + doctor + "'";
		if(orderDate!=null)
			sql += " and orderDate='" + orderDate + "'";
		if (timeSlotObj != 0)
			sql += " and timeSlotObj=" + timeSlotObj;
		if (visiteStateObj != 0)
			sql += " and visiteStateObj=" + visiteStateObj;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUesrObj(rs.getString("uesrObj"));
				orderInfo.setDoctor(rs.getString("doctor"));
				orderInfo.setOrderDate(rs.getTimestamp("orderDate"));
				orderInfo.setTimeSlotObj(rs.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(rs.getInt("visiteStateObj"));
				orderInfo.setIntroduce(rs.getString("introduce"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* ����ԤԼ���󣬽���ԤԼ�����ҵ�� */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ԤԼ */
			String sqlString = "insert into OrderInfo(uesrObj,doctor,orderDate,timeSlotObj,visiteStateObj,introduce) values (";
			sqlString += "'" + orderInfo.getUesrObj() + "',";
			sqlString += "'" + orderInfo.getDoctor() + "',";
			sqlString += "'" + orderInfo.getOrderDate() + "',";
			sqlString += orderInfo.getTimeSlotObj() + ",";
			sqlString += orderInfo.getVisiteStateObj() + ",";
			sqlString += "'" + orderInfo.getIntroduce() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ԤԼ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ԤԼ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ԤԼ */
	public String DeleteOrderInfo(int orderId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderId=" + orderId;
			db.executeUpdate(sqlString);
			result = "ԤԼɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ԤԼɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ԤԼid��ȡ��ԤԼ */
	public OrderInfo GetOrderInfo(int orderId) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderId=" + orderId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderId(rs.getInt("orderId"));
				orderInfo.setUesrObj(rs.getString("uesrObj"));
				orderInfo.setDoctor(rs.getString("doctor"));
				orderInfo.setOrderDate(rs.getTimestamp("orderDate"));
				orderInfo.setTimeSlotObj(rs.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(rs.getInt("visiteStateObj"));
				orderInfo.setIntroduce(rs.getString("introduce"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* ����ԤԼ */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "uesrObj='" + orderInfo.getUesrObj() + "',";
			sql += "doctor='" + orderInfo.getDoctor() + "',";
			sql += "orderDate='" + orderInfo.getOrderDate() + "',";
			sql += "timeSlotObj=" + orderInfo.getTimeSlotObj() + ",";
			sql += "visiteStateObj=" + orderInfo.getVisiteStateObj() + ",";
			sql += "introduce='" + orderInfo.getIntroduce() + "'";
			sql += " where orderId=" + orderInfo.getOrderId();
			db.executeUpdate(sql);
			result = "ԤԼ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ԤԼ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
