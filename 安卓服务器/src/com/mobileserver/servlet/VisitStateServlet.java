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

	/*�������״̬ҵ������*/
	private VisitStateDAO visitStateDAO = new VisitStateDAO();

	/*Ĭ�Ϲ��캯��*/
	public VisitStateServlet() {
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
			/*��ȡ��ѯ����״̬�Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�г���״̬��ѯ*/
			List<VisitState> visitStateList = visitStateDAO.QueryVisitState();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӳ���״̬����ȡ����״̬�������������浽�½��ĳ���״̬���� */ 
			VisitState visitState = new VisitState();
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			visitState.setVisitStateId(visitStateId);
			String visitStateName = new String(request.getParameter("visitStateName").getBytes("iso-8859-1"), "UTF-8");
			visitState.setVisitStateName(visitStateName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = visitStateDAO.AddVisitState(visitState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������״̬����ȡ����״̬��״̬id*/
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = visitStateDAO.DeleteVisitState(visitStateId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³���״̬֮ǰ�ȸ���visitStateId��ѯĳ������״̬*/
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			VisitState visitState = visitStateDAO.GetVisitState(visitStateId);

			// �ͻ��˲�ѯ�ĳ���״̬���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³���״̬����ȡ����״̬�������������浽�½��ĳ���״̬���� */ 
			VisitState visitState = new VisitState();
			int visitStateId = Integer.parseInt(request.getParameter("visitStateId"));
			visitState.setVisitStateId(visitStateId);
			String visitStateName = new String(request.getParameter("visitStateName").getBytes("iso-8859-1"), "UTF-8");
			visitState.setVisitStateName(visitStateName);

			/* ����ҵ���ִ�и��²��� */
			String result = visitStateDAO.UpdateVisitState(visitState);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
