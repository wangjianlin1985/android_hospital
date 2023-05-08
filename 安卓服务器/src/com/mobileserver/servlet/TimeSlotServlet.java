package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TimeSlotDAO;
import com.mobileserver.domain.TimeSlot;

import org.json.JSONStringer;

public class TimeSlotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����ʱ���ҵ������*/
	private TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

	/*Ĭ�Ϲ��캯��*/
	public TimeSlotServlet() {
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
			/*��ȡ��ѯʱ��εĲ�����Ϣ*/

			/*����ҵ���߼���ִ��ʱ��β�ѯ*/
			List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlot();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<TimeSlots>").append("\r\n");
			for (int i = 0; i < timeSlotList.size(); i++) {
				sb.append("	<TimeSlot>").append("\r\n")
				.append("		<timeSlotId>")
				.append(timeSlotList.get(i).getTimeSlotId())
				.append("</timeSlotId>").append("\r\n")
				.append("		<timeSlotName>")
				.append(timeSlotList.get(i).getTimeSlotName())
				.append("</timeSlotName>").append("\r\n")
				.append("	</TimeSlot>").append("\r\n");
			}
			sb.append("</TimeSlots>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(TimeSlot timeSlot: timeSlotList) {
				  stringer.object();
			  stringer.key("timeSlotId").value(timeSlot.getTimeSlotId());
			  stringer.key("timeSlotName").value(timeSlot.getTimeSlotName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ���ʱ��Σ���ȡʱ��β������������浽�½���ʱ��ζ��� */ 
			TimeSlot timeSlot = new TimeSlot();
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			timeSlot.setTimeSlotId(timeSlotId);
			String timeSlotName = new String(request.getParameter("timeSlotName").getBytes("iso-8859-1"), "UTF-8");
			timeSlot.setTimeSlotName(timeSlotName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = timeSlotDAO.AddTimeSlot(timeSlot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��ʱ��Σ���ȡʱ��ε�ʱ���id*/
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = timeSlotDAO.DeleteTimeSlot(timeSlotId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����ʱ���֮ǰ�ȸ���timeSlotId��ѯĳ��ʱ���*/
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			TimeSlot timeSlot = timeSlotDAO.GetTimeSlot(timeSlotId);

			// �ͻ��˲�ѯ��ʱ��ζ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("timeSlotId").value(timeSlot.getTimeSlotId());
			  stringer.key("timeSlotName").value(timeSlot.getTimeSlotName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ����ʱ��Σ���ȡʱ��β������������浽�½���ʱ��ζ��� */ 
			TimeSlot timeSlot = new TimeSlot();
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			timeSlot.setTimeSlotId(timeSlotId);
			String timeSlotName = new String(request.getParameter("timeSlotName").getBytes("iso-8859-1"), "UTF-8");
			timeSlot.setTimeSlotName(timeSlotName);

			/* ����ҵ���ִ�и��²��� */
			String result = timeSlotDAO.UpdateTimeSlot(timeSlot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
