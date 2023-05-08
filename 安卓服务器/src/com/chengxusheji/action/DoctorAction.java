package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.DoctorDAO;
import com.chengxusheji.domain.Doctor;
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DoctorAction extends BaseAction {

	/*图片或文件字段doctorPhoto参数接收*/
	private File doctorPhotoFile;
	private String doctorPhotoFileFileName;
	private String doctorPhotoFileContentType;
	public File getDoctorPhotoFile() {
		return doctorPhotoFile;
	}
	public void setDoctorPhotoFile(File doctorPhotoFile) {
		this.doctorPhotoFile = doctorPhotoFile;
	}
	public String getDoctorPhotoFileFileName() {
		return doctorPhotoFileFileName;
	}
	public void setDoctorPhotoFileFileName(String doctorPhotoFileFileName) {
		this.doctorPhotoFileFileName = doctorPhotoFileFileName;
	}
	public String getDoctorPhotoFileContentType() {
		return doctorPhotoFileContentType;
	}
	public void setDoctorPhotoFileContentType(String doctorPhotoFileContentType) {
		this.doctorPhotoFileContentType = doctorPhotoFileContentType;
	}
    /*界面层需要查询的属性: 医生编号*/
    private String doctorNo;
    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }
    public String getDoctorNo() {
        return this.doctorNo;
    }

    /*界面层需要查询的属性: 所在科室*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 学历*/
    private String education;
    public void setEducation(String education) {
        this.education = education;
    }
    public String getEducation() {
        return this.education;
    }

    /*界面层需要查询的属性: 入院日期*/
    private String inDate;
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getInDate() {
        return this.inDate;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource DepartmentDAO departmentDAO;
    @Resource DoctorDAO doctorDAO;

    /*待操作的Doctor对象*/
    private Doctor doctor;
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Doctor getDoctor() {
        return this.doctor;
    }

    /*跳转到添加Doctor视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Department信息*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        return "add_view";
    }

    /*添加Doctor信息*/
    @SuppressWarnings("deprecation")
    public String AddDoctor() {
        ActionContext ctx = ActionContext.getContext();
        /*验证医生编号是否已经存在*/
        String doctorNo = doctor.getDoctorNo();
        Doctor db_doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);
        if(null != db_doctor) {
            ctx.put("error",  java.net.URLEncoder.encode("该医生编号已经存在!"));
            return "error";
        }
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(doctor.getDepartmentObj().getDepartmentId());
            doctor.setDepartmentObj(departmentObj);
            /*处理医生照片上传*/
            String doctorPhotoPath = "upload/noimage.jpg"; 
       	 	if(doctorPhotoFile != null)
       	 		doctorPhotoPath = photoUpload(doctorPhotoFile,doctorPhotoFileContentType);
       	 	doctor.setDoctorPhoto(doctorPhotoPath);
            doctorDAO.AddDoctor(doctor);
            ctx.put("message",  java.net.URLEncoder.encode("Doctor添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctor添加失败!"));
            return "error";
        }
    }

    /*查询Doctor信息*/
    public String QueryDoctor() {
        if(currentPage == 0) currentPage = 1;
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo, departmentObj, name, education, inDate, currentPage);
        /*计算总的页数和总的记录数*/
        doctorDAO.CalculateTotalPageAndRecordNumber(doctorNo, departmentObj, name, education, inDate);
        /*获取到总的页码数目*/
        totalPage = doctorDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = doctorDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("doctorList",  doctorList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("doctorNo", doctorNo);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("name", name);
        ctx.put("education", education);
        ctx.put("inDate", inDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDoctorOutputToExcel() { 
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo,departmentObj,name,education,inDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Doctor信息记录"; 
        String[] headers = { "医生编号","所在科室","姓名","性别","医生照片","学历","入院日期","联系电话","每日出诊次数"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<doctorList.size();i++) {
        	Doctor doctor = doctorList.get(i); 
        	dataset.add(new String[]{doctor.getDoctorNo(),doctor.getDepartmentObj().getDepartmentName(),
doctor.getName(),doctor.getSex(),doctor.getDoctorPhoto(),doctor.getEducation(),new SimpleDateFormat("yyyy-MM-dd").format(doctor.getInDate()),doctor.getTelephone(),doctor.getVisiteTimes() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Doctor.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Doctor信息*/
    public String FrontQueryDoctor() {
        if(currentPage == 0) currentPage = 1;
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo, departmentObj, name, education, inDate, currentPage);
        /*计算总的页数和总的记录数*/
        doctorDAO.CalculateTotalPageAndRecordNumber(doctorNo, departmentObj, name, education, inDate);
        /*获取到总的页码数目*/
        totalPage = doctorDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = doctorDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("doctorList",  doctorList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("doctorNo", doctorNo);
        ctx.put("departmentObj", departmentObj);
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("name", name);
        ctx.put("education", education);
        ctx.put("inDate", inDate);
        return "front_query_view";
    }

    /*查询要修改的Doctor信息*/
    public String ModifyDoctorQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键doctorNo获取Doctor对象*/
        Doctor doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("doctor",  doctor);
        return "modify_view";
    }

    /*查询要修改的Doctor信息*/
    public String FrontShowDoctorQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键doctorNo获取Doctor对象*/
        Doctor doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("doctor",  doctor);
        return "front_show_view";
    }

    /*更新修改Doctor信息*/
    public String ModifyDoctor() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(doctor.getDepartmentObj().getDepartmentId());
            doctor.setDepartmentObj(departmentObj);
            /*处理医生照片上传*/
            if(doctorPhotoFile != null) {
            	String doctorPhotoPath = photoUpload(doctorPhotoFile,doctorPhotoFileContentType);
            	doctor.setDoctorPhoto(doctorPhotoPath);
            }
            doctorDAO.UpdateDoctor(doctor);
            ctx.put("message",  java.net.URLEncoder.encode("Doctor信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctor信息更新失败!"));
            return "error";
       }
   }

    /*删除Doctor信息*/
    public String DeleteDoctor() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            doctorDAO.DeleteDoctor(doctorNo);
            ctx.put("message",  java.net.URLEncoder.encode("Doctor删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctor删除失败!"));
            return "error";
        }
    }

}
