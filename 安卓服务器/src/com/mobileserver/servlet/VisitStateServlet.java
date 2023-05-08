package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.VisitStateDAO;
import com.mobileserver.domain.VisitState;

import org.json.JSONStringer;

public class VisitStateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造出诊状态业务层对象*/
	private VisitStateDAO visitStateDAO = new VisitStateDAO();

	/*默认构造函数*/
	public VisitStateServlet() {
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
			/*获取查询出诊状态的参数信息*/

			/*调用业务逻辑层执行出诊状态查询*/
			List<VisitState> visitStateList = visitStateDAO.QueryVisitState();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<VisitStates>").append("\r\n");
			for (int i = 0; i < visitStateList.size(); i++) {
				sb.append("	<VisitState>").append("\r\n")
				.append("		<visitStateId>")
				.append(visitStateList.get(i).getVisitStateId())
				.append("</visitStateId>").append("\r\n")
				.append("		<visitStateName>")
				.append(visitStateList.get(i).getVisitStateName())
				.append("</visitStateName>").append("\r\n")
				.append("	</VisitState>").append("\r\n");
			}
			sb.append("</VisitStates>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(VisitState visitState: visitStateList) {
				  stringer.object();
			  stringer.key("visitStateId").value(visitState.getVisitStateId());
			  stringer.key("visitStateName").value(visitState.getVisitStateName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加出诊状态：获取出诊状态参数，参数保存到新建的出诊状态对象 */ 
			VisitState visitState = new VisitState();
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			visitState.setVisitStateId(visitStateId);
			String visitStateName = new String(request.getParameter("visitStateName").getBytes("iso-8859-1"), "UTF-8");
			visitState.setVisitStateName(visitStateName);

			/* 调用业务层执行添加操作 */
			String result = visitStateDAO.AddVisitState(visitState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除出诊状态：获取出诊状态的状态id*/
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			/*调用业务逻辑层执行删除操作*/
			String result = visitStateDAO.DeleteVisitState(visitStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新出诊状态之前先根据visitStateId查询某个出诊状态*/
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			VisitState visitState = visitStateDAO.GetVisitState(visitStateId);

			// 客户端查询的出诊状态对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("visitStateId").value(visitState.getVisitStateId());
			  stringer.key("visitStateName").value(visitState.getVisitStateName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新出诊状态：获取出诊状态参数，参数保存到新建的出诊状态对象 */ 
			VisitState visitState = new VisitState();
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			visitState.setVisitStateId(visitStateId);
			String visitStateName = new String(request.getParameter("visitStateName").getBytes("iso-8859-1"), "UTF-8");
			visitState.setVisitStateName(visitStateName);

			/* 调用业务层执行更新操作 */
			String result = visitStateDAO.UpdateVisitState(visitState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
