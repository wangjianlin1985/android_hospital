package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DepartmentDAO;
import com.mobileserver.domain.Department;

import org.json.JSONStringer;

public class DepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������ҵ������*/
	private DepartmentDAO departmentDAO = new DepartmentDAO();

	/*Ĭ�Ϲ��캯��*/
	public DepartmentServlet() {
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
			/*��ȡ��ѯ���ҵĲ�����Ϣ*/

			/*����ҵ���߼���ִ�п��Ҳ�ѯ*/
			List<Department> departmentList = departmentDAO.QueryDepartment();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Departments>").append("\r\n");
			for (int i = 0; i < departmentList.size(); i++) {
				sb.append("	<Department>").append("\r\n")
				.append("		<departmentId>")
				.append(departmentList.get(i).getDepartmentId())
				.append("</departmentId>").append("\r\n")
				.append("		<departmentName>")
				.append(departmentList.get(i).getDepartmentName())
				.append("</departmentName>").append("\r\n")
				.append("	</Department>").append("\r\n");
			}
			sb.append("</Departments>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Department department: departmentList) {
				  stringer.object();
			  stringer.key("departmentId").value(department.getDepartmentId());
			  stringer.key("departmentName").value(department.getDepartmentName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӿ��ң���ȡ���Ҳ������������浽�½��Ŀ��Ҷ��� */ 
			Department department = new Department();
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			department.setDepartmentId(departmentId);
			String departmentName = new String(request.getParameter("departmentName").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentName(departmentName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = departmentDAO.AddDepartment(department);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�����ң���ȡ���ҵĿ���id*/
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = departmentDAO.DeleteDepartment(departmentId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���¿���֮ǰ�ȸ���departmentId��ѯĳ������*/
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			Department department = departmentDAO.GetDepartment(departmentId);

			// �ͻ��˲�ѯ�Ŀ��Ҷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("departmentId").value(department.getDepartmentId());
			  stringer.key("departmentName").value(department.getDepartmentName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���¿��ң���ȡ���Ҳ������������浽�½��Ŀ��Ҷ��� */ 
			Department department = new Department();
			int departmentId = Integer.parseInt(request.getParameter("departmentId"));
			department.setDepartmentId(departmentId);
			String departmentName = new String(request.getParameter("departmentName").getBytes("iso-8859-1"), "UTF-8");
			department.setDepartmentName(departmentName);

			/* ����ҵ���ִ�и��²��� */
			String result = departmentDAO.UpdateDepartment(department);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
