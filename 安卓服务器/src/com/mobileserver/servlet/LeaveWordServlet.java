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

	/*��������ҵ������*/
	private LeaveWordDAO leaveWordDAO = new LeaveWordDAO();

	/*Ĭ�Ϲ��캯��*/
	public LeaveWordServlet() {
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
			/*��ȡ��ѯ���ԵĲ�����Ϣ*/
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

			/*����ҵ���߼���ִ�����Բ�ѯ*/
			List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWord(title,userObj,replyTime,replyDoctor);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ������ԣ���ȡ���Բ������������浽�½������Զ��� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = leaveWordDAO.AddLeaveWord(leaveWord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ԣ���ȡ���Ե�����id*/
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = leaveWordDAO.DeleteLeaveWord(leaveWordId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*��������֮ǰ�ȸ���leaveWordId��ѯĳ������*/
			int leaveWordId = Integer.parseInt(request.getParameter("leaveWordId"));
			LeaveWord leaveWord = leaveWordDAO.GetLeaveWord(leaveWordId);

			// �ͻ��˲�ѯ�����Զ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �������ԣ���ȡ���Բ������������浽�½������Զ��� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = leaveWordDAO.UpdateLeaveWord(leaveWord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
