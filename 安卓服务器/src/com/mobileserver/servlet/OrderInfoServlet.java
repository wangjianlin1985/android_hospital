package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderInfoDAO;
import com.mobileserver.domain.OrderInfo;

import org.json.JSONStringer;

public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ԤԼҵ������*/
	private OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯԤԼ�Ĳ�����Ϣ*/
			String uesrObj = "";
			if (request.getParameter("uesrObj") != null)
				uesrObj = request.getParameter("uesrObj");
			String doctor = "";
			if (request.getParameter("doctor") != null)
				doctor = request.getParameter("doctor");
			Timestamp orderDate = null;
			if (request.getParameter("orderDate") != null)
				orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			int timeSlotObj = 0;
			if (request.getParameter("timeSlotObj") != null)
				timeSlotObj = Integer.parseInt(request.getParameter("timeSlotObj"));
			int visiteStateObj = 0;
			if (request.getParameter("visiteStateObj") != null)
				visiteStateObj = Integer.parseInt(request.getParameter("visiteStateObj"));

			/*����ҵ���߼���ִ��ԤԼ��ѯ*/
			List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfo(uesrObj,doctor,orderDate,timeSlotObj,visiteStateObj);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderInfos>").append("\r\n");
			for (int i = 0; i < orderInfoList.size(); i++) {
				sb.append("	<OrderInfo>").append("\r\n")
				.append("		<orderId>")
				.append(orderInfoList.get(i).getOrderId())
				.append("</orderId>").append("\r\n")
				.append("		<uesrObj>")
				.append(orderInfoList.get(i).getUesrObj())
				.append("</uesrObj>").append("\r\n")
				.append("		<doctor>")
				.append(orderInfoList.get(i).getDoctor())
				.append("</doctor>").append("\r\n")
				.append("		<orderDate>")
				.append(orderInfoList.get(i).getOrderDate())
				.append("</orderDate>").append("\r\n")
				.append("		<timeSlotObj>")
				.append(orderInfoList.get(i).getTimeSlotObj())
				.append("</timeSlotObj>").append("\r\n")
				.append("		<visiteStateObj>")
				.append(orderInfoList.get(i).getVisiteStateObj())
				.append("</visiteStateObj>").append("\r\n")
				.append("		<introduce>")
				.append(orderInfoList.get(i).getIntroduce())
				.append("</introduce>").append("\r\n")
				.append("	</OrderInfo>").append("\r\n");
			}
			sb.append("</OrderInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderInfo orderInfo: orderInfoList) {
				  stringer.object();
			  stringer.key("orderId").value(orderInfo.getOrderId());
			  stringer.key("uesrObj").value(orderInfo.getUesrObj());
			  stringer.key("doctor").value(orderInfo.getDoctor());
			  stringer.key("orderDate").value(orderInfo.getOrderDate());
			  stringer.key("timeSlotObj").value(orderInfo.getTimeSlotObj());
			  stringer.key("visiteStateObj").value(orderInfo.getVisiteStateObj());
			  stringer.key("introduce").value(orderInfo.getIntroduce());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ԤԼ����ȡԤԼ�������������浽�½���ԤԼ���� */ 
			OrderInfo orderInfo = new OrderInfo();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			orderInfo.setOrderId(orderId);
			String uesrObj = new String(request.getParameter("uesrObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUesrObj(uesrObj);
			String doctor = new String(request.getParameter("doctor").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setDoctor(doctor);
			Timestamp orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			orderInfo.setOrderDate(orderDate);
			int timeSlotObj = Integer.parseInt(request.getParameter("timeSlotObj"));
			orderInfo.setTimeSlotObj(timeSlotObj);
			int visiteStateObj = Integer.parseInt(request.getParameter("visiteStateObj"));
			orderInfo.setVisiteStateObj(visiteStateObj);
			String introduce = new String(request.getParameter("introduce").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setIntroduce(introduce);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderInfoDAO.AddOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ԤԼ����ȡԤԼ��ԤԼid*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderInfoDAO.DeleteOrderInfo(orderId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ԤԼ֮ǰ�ȸ���orderId��ѯĳ��ԤԼ*/
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			OrderInfo orderInfo = orderInfoDAO.GetOrderInfo(orderId);

			// �ͻ��˲�ѯ��ԤԼ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderId").value(orderInfo.getOrderId());
			  stringer.key("uesrObj").value(orderInfo.getUesrObj());
			  stringer.key("doctor").value(orderInfo.getDoctor());
			  stringer.key("orderDate").value(orderInfo.getOrderDate());
			  stringer.key("timeSlotObj").value(orderInfo.getTimeSlotObj());
			  stringer.key("visiteStateObj").value(orderInfo.getVisiteStateObj());
			  stringer.key("introduce").value(orderInfo.getIntroduce());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ԤԼ����ȡԤԼ�������������浽�½���ԤԼ���� */ 
			OrderInfo orderInfo = new OrderInfo();
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			orderInfo.setOrderId(orderId);
			String uesrObj = new String(request.getParameter("uesrObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUesrObj(uesrObj);
			String doctor = new String(request.getParameter("doctor").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setDoctor(doctor);
			Timestamp orderDate = Timestamp.valueOf(request.getParameter("orderDate"));
			orderInfo.setOrderDate(orderDate);
			int timeSlotObj = Integer.parseInt(request.getParameter("timeSlotObj"));
			orderInfo.setTimeSlotObj(timeSlotObj);
			int visiteStateObj = Integer.parseInt(request.getParameter("visiteStateObj"));
			orderInfo.setVisiteStateObj(visiteStateObj);
			String introduce = new String(request.getParameter("introduce").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setIntroduce(introduce);

			/* ����ҵ���ִ�и��²��� */
			String result = orderInfoDAO.UpdateOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
