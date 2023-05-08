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

	/*构造时间段业务层对象*/
	private TimeSlotDAO timeSlotDAO = new TimeSlotDAO();

	/*默认构造函数*/
	public TimeSlotServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询时间段的参数信息*/

			/*调用业务逻辑层执行时间段查询*/
			List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlot();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加时间段：获取时间段参数，参数保存到新建的时间段对象 */ 
			TimeSlot timeSlot = new TimeSlot();
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			timeSlot.setTimeSlotId(timeSlotId);
			String timeSlotName = new String(request.getParameter("timeSlotName").getBytes("iso-8859-1"), "UTF-8");
			timeSlot.setTimeSlotName(timeSlotName);

			/* 调用业务层执行添加操作 */
			String result = timeSlotDAO.AddTimeSlot(timeSlot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除时间段：获取时间段的时间段id*/
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			/*调用业务逻辑层执行删除操作*/
			String result = timeSlotDAO.DeleteTimeSlot(timeSlotId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新时间段之前先根据timeSlotId查询某个时间段*/
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			TimeSlot timeSlot = timeSlotDAO.GetTimeSlot(timeSlotId);

			// 客户端查询的时间段对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新时间段：获取时间段参数，参数保存到新建的时间段对象 */ 
			TimeSlot timeSlot = new TimeSlot();
			int timeSlotId = Integer.parseInt(request.getParameter("timeSlotId"));
			timeSlot.setTimeSlotId(timeSlotId);
			String timeSlotName = new String(request.getParameter("timeSlotName").getBytes("iso-8859-1"), "UTF-8");
			timeSlot.setTimeSlotName(timeSlotName);

			/* 调用业务层执行更新操作 */
			String result = timeSlotDAO.UpdateTimeSlot(timeSlot);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
