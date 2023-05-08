package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.UserInfoDAO;
import com.mobileserver.domain.UserInfo;

import org.json.JSONStringer;

public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����û�ҵ������*/
	private UserInfoDAO userInfoDAO = new UserInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public UserInfoServlet() {
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
			/*��ȡ��ѯ�û��Ĳ�����Ϣ*/
			String user_name = request.getParameter("user_name");
			user_name = user_name == null ? "" : new String(request.getParameter(
					"user_name").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp birthday = null;
			if (request.getParameter("birthday") != null)
				birthday = Timestamp.valueOf(request.getParameter("birthday"));
			String jiguan = request.getParameter("jiguan");
			jiguan = jiguan == null ? "" : new String(request.getParameter(
					"jiguan").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ���û���ѯ*/
			List<UserInfo> userInfoList = userInfoDAO.QueryUserInfo(user_name,name,birthday,jiguan);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<UserInfos>").append("\r\n");
			for (int i = 0; i < userInfoList.size(); i++) {
				sb.append("	<UserInfo>").append("\r\n")
				.append("		<user_name>")
				.append(userInfoList.get(i).getUser_name())
				.append("</user_name>").append("\r\n")
				.append("		<password>")
				.append(userInfoList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(userInfoList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(userInfoList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<userPhoto>")
				.append(userInfoList.get(i).getUserPhoto())
				.append("</userPhoto>").append("\r\n")
				.append("		<birthday>")
				.append(userInfoList.get(i).getBirthday())
				.append("</birthday>").append("\r\n")
				.append("		<jiguan>")
				.append(userInfoList.get(i).getJiguan())
				.append("</jiguan>").append("\r\n")
				.append("		<telephone>")
				.append(userInfoList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<address>")
				.append(userInfoList.get(i).getAddress())
				.append("</address>").append("\r\n")
				.append("	</UserInfo>").append("\r\n");
			}
			sb.append("</UserInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(UserInfo userInfo: userInfoList) {
				  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("sex").value(userInfo.getSex());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("birthday").value(userInfo.getBirthday());
			  stringer.key("jiguan").value(userInfo.getJiguan());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("address").value(userInfo.getAddress());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ����û�����ȡ�û��������������浽�½����û����� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSex(sex);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			userInfo.setBirthday(birthday);
			String jiguan = new String(request.getParameter("jiguan").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setJiguan(jiguan);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);

			/* ����ҵ���ִ����Ӳ��� */
			String result = userInfoDAO.AddUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���û�����ȡ�û����û���*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = userInfoDAO.DeleteUserInfo(user_name);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�����û�֮ǰ�ȸ���user_name��ѯĳ���û�*/
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			UserInfo userInfo = userInfoDAO.GetUserInfo(user_name);

			// �ͻ��˲�ѯ���û����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("user_name").value(userInfo.getUser_name());
			  stringer.key("password").value(userInfo.getPassword());
			  stringer.key("name").value(userInfo.getName());
			  stringer.key("sex").value(userInfo.getSex());
			  stringer.key("userPhoto").value(userInfo.getUserPhoto());
			  stringer.key("birthday").value(userInfo.getBirthday());
			  stringer.key("jiguan").value(userInfo.getJiguan());
			  stringer.key("telephone").value(userInfo.getTelephone());
			  stringer.key("address").value(userInfo.getAddress());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* �����û�����ȡ�û��������������浽�½����û����� */ 
			UserInfo userInfo = new UserInfo();
			String user_name = new String(request.getParameter("user_name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUser_name(user_name);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setSex(sex);
			String userPhoto = new String(request.getParameter("userPhoto").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setUserPhoto(userPhoto);
			Timestamp birthday = Timestamp.valueOf(request.getParameter("birthday"));
			userInfo.setBirthday(birthday);
			String jiguan = new String(request.getParameter("jiguan").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setJiguan(jiguan);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setTelephone(telephone);
			String address = new String(request.getParameter("address").getBytes("iso-8859-1"), "UTF-8");
			userInfo.setAddress(address);

			/* ����ҵ���ִ�и��²��� */
			String result = userInfoDAO.UpdateUserInfo(userInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
