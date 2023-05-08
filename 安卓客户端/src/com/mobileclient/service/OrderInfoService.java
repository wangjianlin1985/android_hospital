package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*预约管理业务逻辑层*/
public class OrderInfoService {
	/* 添加预约 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("uesrObj", orderInfo.getUesrObj());
		params.put("doctor", orderInfo.getDoctor());
		params.put("orderDate", orderInfo.getOrderDate().toString());
		params.put("timeSlotObj", orderInfo.getTimeSlotObj() + "");
		params.put("visiteStateObj", orderInfo.getVisiteStateObj() + "");
		params.put("introduce", orderInfo.getIntroduce());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询预约 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&uesrObj=" + URLEncoder.encode(queryConditionOrderInfo.getUesrObj(), "UTF-8") + "";
			urlString += "&doctor=" + URLEncoder.encode(queryConditionOrderInfo.getDoctor(), "UTF-8") + "";
			if(queryConditionOrderInfo.getOrderDate() != null) {
				urlString += "&orderDate=" + URLEncoder.encode(queryConditionOrderInfo.getOrderDate().toString(), "UTF-8");
			}
			urlString += "&timeSlotObj=" + queryConditionOrderInfo.getTimeSlotObj();
			urlString += "&visiteStateObj=" + queryConditionOrderInfo.getVisiteStateObj();
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUesrObj(object.getString("uesrObj"));
				orderInfo.setDoctor(object.getString("doctor"));
				orderInfo.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				orderInfo.setTimeSlotObj(object.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(object.getInt("visiteStateObj"));
				orderInfo.setIntroduce(object.getString("introduce"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}

	/* 更新预约 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderInfo.getOrderId() + "");
		params.put("uesrObj", orderInfo.getUesrObj());
		params.put("doctor", orderInfo.getDoctor());
		params.put("orderDate", orderInfo.getOrderDate().toString());
		params.put("timeSlotObj", orderInfo.getTimeSlotObj() + "");
		params.put("visiteStateObj", orderInfo.getVisiteStateObj() + "");
		params.put("introduce", orderInfo.getIntroduce());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除预约 */
	public String DeleteOrderInfo(int orderId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "预约信息删除失败!";
		}
	}

	/* 根据预约id获取预约对象 */
	public OrderInfo GetOrderInfo(int orderId)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderId", orderId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderId(object.getInt("orderId"));
				orderInfo.setUesrObj(object.getString("uesrObj"));
				orderInfo.setDoctor(object.getString("doctor"));
				orderInfo.setOrderDate(Timestamp.valueOf(object.getString("orderDate")));
				orderInfo.setTimeSlotObj(object.getInt("timeSlotObj"));
				orderInfo.setVisiteStateObj(object.getInt("visiteStateObj"));
				orderInfo.setIntroduce(object.getString("introduce"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
