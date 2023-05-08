package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LeaveWordDAO;
import com.mobileserver.domain.LeaveWord;

import org.json.JSONStringer;

public class LeaveWordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造留言业务层对象*/
	private LeaveWordDAO leaveWordDAO = new LeaveWordDAO();

	/*默认构造函数*/
	public LeaveWordServlet() {
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
			/*获取查询留言的参数信息*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String replyTime = request.getParameter("replyTime");
			replyTime = replyTime == null ? "" : new String(request.getParameter(
					"replyTime").getBytes("iso-8859-1"), "UTF-8");
			String replyDoctor = request.getParameter("replyDoctor");
			replyDoctor = replyDoctor == null ? "" : new String(request.getParameter(
					"replyDoctor").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行留言查询*/
			List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWord(title,userObj,replyTime,replyDoctor);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LeaveWords>").append("\r\n");
			for (int i = 0; i < leaveWordList.size(); i++) {
				sb.append("	<LeaveWord>").append("\r\n")
				.append("		<leaveWordId>")
				.append(leaveWordList.get(i).getLeaveWordId())
				.append("</leaveWordId>").append("\r\n")
				.append("		<title>")
				.append(leaveWordList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(leaveWordList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<addTime>")
				.append(leaveWordList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("		<userObj>")
				.append(leaveWordList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<replyContent>")
				.append(leaveWordList.get(i).getReplyContent())
				.append("</replyContent>").append("\r\n")
				.append("		<replyTime>")
				.append(leaveWordList.get(i).getReplyTime())
				.append("</replyTime>").append("\r\n")
				.append("		<replyDoctor>")
				.append(leaveWordList.get(i).getReplyDoctor())
				.append("</replyDoctor>").append("\r\n")
				.append("	</LeaveWord>").append("\r\n");
			}
			sb.append("</LeaveWords>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LeaveWord leaveWord: leaveWordList) {
				  stringer.object();
			  stringer.key("leaveWordId").value(leaveWord.getLeaveWordId());
			  stringer.key("title").value(leaveWord.getTitle());
			  stringer.key("content").value(leaveWord.getContent());
			  stringer.key("addTime").value(leaveWord.getAddTime());
			  stringer.key("userObj").value(leaveWord.getUserObj());
			  stringer.key("replyContent").value(leaveWord.getReplyContent());
			  stringer.key("replyTime").value(leaveWord.getReplyTime());
			  stringer.key("replyDoctor").value(leaveWord.getReplyDoctor());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加留言：获取留言参数，参数保存到新建的留言对象 */ 
			LeaveWord leaveWord = new LeaveWord();
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			leaveWord.setLeaveWordId(leaveWordId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setAddTime(addTime);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setUserObj(userObj);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyContent(replyContent);
			String replyTime = new String(request.getParameter("replyTime").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyTime(replyTime);
			String replyDoctor = new String(request.getParameter("replyDoctor").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyDoctor(replyDoctor);

			/* 调用业务层执行添加操作 */
			String result = leaveWordDAO.AddLeaveWord(leaveWord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除留言：获取留言的留言id*/
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			/*调用业务逻辑层执行删除操作*/
			String result = leaveWordDAO.DeleteLeaveWord(leaveWordId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新留言之前先根据leaveWordId查询某个留言*/
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			LeaveWord leaveWord = leaveWordDAO.GetLeaveWord(leaveWordId);

			// 客户端查询的留言对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("leaveWordId").value(leaveWord.getLeaveWordId());
			  stringer.key("title").value(leaveWord.getTitle());
			  stringer.key("content").value(leaveWord.getContent());
			  stringer.key("addTime").value(leaveWord.getAddTime());
			  stringer.key("userObj").value(leaveWord.getUserObj());
			  stringer.key("replyContent").value(leaveWord.getReplyContent());
			  stringer.key("replyTime").value(leaveWord.getReplyTime());
			  stringer.key("replyDoctor").value(leaveWord.getReplyDoctor());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新留言：获取留言参数，参数保存到新建的留言对象 */ 
			LeaveWord leaveWord = new LeaveWord();
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			leaveWord.setLeaveWordId(leaveWordId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setContent(content);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setAddTime(addTime);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setUserObj(userObj);
			String replyContent = new String(request.getParameter("replyContent").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyContent(replyContent);
			String replyTime = new String(request.getParameter("replyTime").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyTime(replyTime);
			String replyDoctor = new String(request.getParameter("replyDoctor").getBytes("iso-8859-1"), "UTF-8");
			leaveWord.setReplyDoctor(replyDoctor);

			/* 调用业务层执行更新操作 */
			String result = leaveWordDAO.UpdateLeaveWord(leaveWord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
