package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.VisitState;
import com.mobileclient.util.HttpUtil;

/*出诊状态管理业务逻辑层*/
public class VisitStateService {
	/* 添加出诊状态 */
	public String AddVisitState(VisitState visitState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitState.getVisitStateId() + "");
		params.put("visitStateName", visitState.getVisitStateName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询出诊状态 */
	public List<VisitState> QueryVisitState(VisitState queryConditionVisitState) throws Exception {
		String urlString = HttpUtil.BASE_URL + "VisitStateServlet?action=query";
		if(queryConditionVisitState != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		VisitStateListHandler visitStateListHander = new VisitStateListHandler();
		xr.setContentHandler(visitStateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<VisitState> visitStateList = visitStateListHander.getVisitStateList();
		return visitStateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<VisitState> visitStateList = new ArrayList<VisitState>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				VisitState visitState = new VisitState();
				visitState.setVisitStateId(object.getInt("visitStateId"));
				visitState.setVisitStateName(object.getString("visitStateName"));
				visitStateList.add(visitState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return visitStateList;
	}

	/* 更新出诊状态 */
	public String UpdateVisitState(VisitState visitState) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitState.getVisitStateId() + "");
		params.put("visitStateName", visitState.getVisitStateName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除出诊状态 */
	public String DeleteVisitState(int visitStateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitStateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "出诊状态信息删除失败!";
		}
	}

	/* 根据状态id获取出诊状态对象 */
	public VisitState GetVisitState(int visitStateId)  {
		List<VisitState> visitStateList = new ArrayList<VisitState>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("visitStateId", visitStateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "VisitStateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				VisitState visitState = new VisitState();
				visitState.setVisitStateId(object.getInt("visitStateId"));
				visitState.setVisitStateName(object.getString("visitStateName"));
				visitStateList.add(visitState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = visitStateList.size();
		if(size>0) return visitStateList.get(0); 
		else return null; 
	}
}
