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
	/* �������״̬���󣬽��г���״̬�����ҵ�� */
	public String AddVisitState(VisitState visitState) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³���״̬ */
			String sqlString = "insert into VisitState(visitStateName) values (";
			sqlString += "'" + visitState.getVisitStateName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����״̬��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������״̬ */
	public String DeleteVisitState(int visitStateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from VisitState where visitStateId=" + visitStateId;
			db.executeUpdate(sqlString);
			result = "����״̬ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����״̬id��ȡ������״̬ */
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
	/* ���³���״̬ */
	public String UpdateVisitState(VisitState visitState) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update VisitState set ";
			sql += "visitStateName='" + visitState.getVisitStateName() + "'";
			sql += " where visitStateId=" + visitState.getVisitStateId();
			db.executeUpdate(sql);
			result = "����״̬���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����״̬����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
