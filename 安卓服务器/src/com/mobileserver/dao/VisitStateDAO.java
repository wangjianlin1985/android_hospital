package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.VisitState;
import com.mobileserver.util.DB;

public class VisitStateDAO {

	public List<VisitState> QueryVisitState() {
		List<VisitState> visitStateList = new ArrayList<VisitState>();
		DB db = new DB();
		String sql = "select * from VisitState where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				VisitState visitState = new VisitState();
				visitState.setVisitStateId(rs.getInt("visitStateId"));
				visitState.setVisitStateName(rs.getString("visitStateName"));
				visitStateList.add(visitState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return visitStateList;
	}
	/* 传入出诊状态对象，进行出诊状态的添加业务 */
	public String AddVisitState(VisitState visitState) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新出诊状态 */
			String sqlString = "insert into VisitState(visitStateName) values (";
			sqlString += "'" + visitState.getVisitStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "出诊状态添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "出诊状态添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除出诊状态 */
	public String DeleteVisitState(int visitStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from VisitState where visitStateId=" + visitStateId;
			db.executeUpdate(sqlString);
			result = "出诊状态删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "出诊状态删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据状态id获取到出诊状态 */
	public VisitState GetVisitState(int visitStateId) {
		VisitState visitState = null;
		DB db = new DB();
		String sql = "select * from VisitState where visitStateId=" + visitStateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				visitState = new VisitState();
				visitState.setVisitStateId(rs.getInt("visitStateId"));
				visitState.setVisitStateName(rs.getString("visitStateName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return visitState;
	}
	/* 更新出诊状态 */
	public String UpdateVisitState(VisitState visitState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update VisitState set ";
			sql += "visitStateName='" + visitState.getVisitStateName() + "'";
			sql += " where visitStateId=" + visitState.getVisitStateId();
			db.executeUpdate(sql);
			result = "出诊状态更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "出诊状态更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
