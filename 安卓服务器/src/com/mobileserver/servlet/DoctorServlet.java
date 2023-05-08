package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.DoctorDAO;
import com.mobileserver.domain.Doctor;

import org.json.JSONStringer;

public class DoctorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造医生业务层对象*/
	private DoctorDAO doctorDAO = new DoctorDAO();

	/*默认构造函数*/
	public DoctorServlet() {
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
			/*获取查询医生的参数信息*/
			String doctorNo = request.getParameter("doctorNo");
			doctorNo = doctorNo == null ? "" : new String(request.getParameter(
					"doctorNo").getBytes("iso-8859-1"), "UTF-8");
			int departmentObj = 0;
			if (request.getParameter("departmentObj") != null)
				departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			String education = request.getParameter("education");
			education = education == null ? "" : new String(request.getParameter(
					"education").getBytes("iso-8859-1"), "UTF-8");
			Timestamp inDate = null;
			if (request.getParameter("inDate") != null)
				inDate = Timestamp.valueOf(request.getParameter("inDate"));

			/*调用业务逻辑层执行医生查询*/
			List<Doctor> doctorList = doctorDAO.QueryDoctor(doctorNo,departmentObj,name,education,inDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Doctors>").append("\r\n");
			for (int i = 0; i < doctorList.size(); i++) {
				sb.append("	<Doctor>").append("\r\n")
				.append("		<doctorNo>")
				.append(doctorList.get(i).getDoctorNo())
				.append("</doctorNo>").append("\r\n")
				.append("		<password>")
				.append(doctorList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<departmentObj>")
				.append(doctorList.get(i).getDepartmentObj())
				.append("</departmentObj>").append("\r\n")
				.append("		<name>")
				.append(doctorList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(doctorList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<doctorPhoto>")
				.append(doctorList.get(i).getDoctorPhoto())
				.append("</doctorPhoto>").append("\r\n")
				.append("		<education>")
				.append(doctorList.get(i).getEducation())
				.append("</education>").append("\r\n")
				.append("		<inDate>")
				.append(doctorList.get(i).getInDate())
				.append("</inDate>").append("\r\n")
				.append("		<telephone>")
				.append(doctorList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<visiteTimes>")
				.append(doctorList.get(i).getVisiteTimes())
				.append("</visiteTimes>").append("\r\n")
				.append("		<memo>")
				.append(doctorList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("	</Doctor>").append("\r\n");
			}
			sb.append("</Doctors>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Doctor doctor: doctorList) {
				  stringer.object();
			  stringer.key("doctorNo").value(doctor.getDoctorNo());
			  stringer.key("password").value(doctor.getPassword());
			  stringer.key("departmentObj").value(doctor.getDepartmentObj());
			  stringer.key("name").value(doctor.getName());
			  stringer.key("sex").value(doctor.getSex());
			  stringer.key("doctorPhoto").value(doctor.getDoctorPhoto());
			  stringer.key("education").value(doctor.getEducation());
			  stringer.key("inDate").value(doctor.getInDate());
			  stringer.key("telephone").value(doctor.getTelephone());
			  stringer.key("visiteTimes").value(doctor.getVisiteTimes());
			  stringer.key("memo").value(doctor.getMemo());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加医生：获取医生参数，参数保存到新建的医生对象 */ 
			Doctor doctor = new Doctor();
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorNo(doctorNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			doctor.setPassword(password);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			doctor.setDepartmentObj(departmentObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			doctor.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSex(sex);
			String doctorPhoto = new String(request.getParameter("doctorPhoto").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorPhoto(doctorPhoto);
			String education = new String(request.getParameter("education").getBytes("iso-8859-1"), "UTF-8");
			doctor.setEducation(education);
			Timestamp inDate = Timestamp.valueOf(request.getParameter("inDate"));
			doctor.setInDate(inDate);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			doctor.setTelephone(telephone);
			int visiteTimes = Integer.parseInt(request.getParameter("visiteTimes"));
			doctor.setVisiteTimes(visiteTimes);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setMemo(memo);

			/* 调用业务层执行添加操作 */
			String result = doctorDAO.AddDoctor(doctor);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除医生：获取医生的医生编号*/
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = doctorDAO.DeleteDoctor(doctorNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新医生之前先根据doctorNo查询某个医生*/
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			Doctor doctor = doctorDAO.GetDoctor(doctorNo);

			// 客户端查询的医生对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("doctorNo").value(doctor.getDoctorNo());
			  stringer.key("password").value(doctor.getPassword());
			  stringer.key("departmentObj").value(doctor.getDepartmentObj());
			  stringer.key("name").value(doctor.getName());
			  stringer.key("sex").value(doctor.getSex());
			  stringer.key("doctorPhoto").value(doctor.getDoctorPhoto());
			  stringer.key("education").value(doctor.getEducation());
			  stringer.key("inDate").value(doctor.getInDate());
			  stringer.key("telephone").value(doctor.getTelephone());
			  stringer.key("visiteTimes").value(doctor.getVisiteTimes());
			  stringer.key("memo").value(doctor.getMemo());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新医生：获取医生参数，参数保存到新建的医生对象 */ 
			Doctor doctor = new Doctor();
			String doctorNo = new String(request.getParameter("doctorNo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorNo(doctorNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			doctor.setPassword(password);
			int departmentObj = Integer.parseInt(request.getParameter("departmentObj"));
			doctor.setDepartmentObj(departmentObj);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			doctor.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			doctor.setSex(sex);
			String doctorPhoto = new String(request.getParameter("doctorPhoto").getBytes("iso-8859-1"), "UTF-8");
			doctor.setDoctorPhoto(doctorPhoto);
			String education = new String(request.getParameter("education").getBytes("iso-8859-1"), "UTF-8");
			doctor.setEducation(education);
			Timestamp inDate = Timestamp.valueOf(request.getParameter("inDate"));
			doctor.setInDate(inDate);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			doctor.setTelephone(telephone);
			int visiteTimes = Integer.parseInt(request.getParameter("visiteTimes"));
			doctor.setVisiteTimes(visiteTimes);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			doctor.setMemo(memo);

			/* 调用业务层执行更新操作 */
			String result = doctorDAO.UpdateDoctor(doctor);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
