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

	/*ͼƬ���ļ��ֶ�doctorPhoto��������*/
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
    /*�������Ҫ��ѯ������: ҽ�����*/
    private String doctorNo;
    public void setDoctorNo(String doctorNo) {
        this.doctorNo = doctorNo;
    }
    public String getDoctorNo() {
        return this.doctorNo;
    }

    /*�������Ҫ��ѯ������: ���ڿ���*/
    private Department departmentObj;
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }
    public Department getDepartmentObj() {
        return this.departmentObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: ѧ��*/
    private String education;
    public void setEducation(String education) {
        this.education = education;
    }
    public String getEducation() {
        return this.education;
    }

    /*�������Ҫ��ѯ������: ��Ժ����*/
    private String inDate;
    public void setInDate(String inDate) {
        this.inDate = inDate;
    }
    public String getInDate() {
        return this.inDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource DepartmentDAO departmentDAO;
    @Resource DoctorDAO doctorDAO;

    /*��������Doctor����*/
    private Doctor doctor;
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Doctor getDoctor() {
        return this.doctor;
    }

    /*��ת�����Doctor��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Department��Ϣ*/
        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        return "add_view";
    }

    /*���Doctor��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDoctor() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤ҽ������Ƿ��Ѿ�����*/
        String doctorNo = doctor.getDoctorNo();
        Doctor db_doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);
        if(null != db_doctor) {
            ctx.put("error",  java.net.URLEncoder.encode("��ҽ������Ѿ�����!"));
            return "error";
        }
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(doctor.getDepartmentObj().getDepartmentId());
            doctor.setDepartmentObj(departmentObj);
            /*����ҽ����Ƭ�ϴ�*/
            String doctorPhotoPath = "upload/noimage.jpg"; 
       	 	if(doctorPhotoFile != null)
       	 		doctorPhotoPath = photoUpload(doctorPhotoFile,doctorPhotoFileContentType);
       	 	doctor.setDoctorPhoto(doctorPhotoPath);
            doctorDAO.AddDoctor(doctor);
            ctx.put("message",  java.net.URLEncoder.encode("Doctor��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctor���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDoctor��Ϣ*/
    public String QueryDoctor() {
        if(currentPage == 0) currentPage = 1;
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo, departmentObj, name, education, inDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        doctorDAO.CalculateTotalPageAndRecordNumber(doctorNo, departmentObj, name, education, inDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = doctorDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryDoctorOutputToExcel() { 
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo,departmentObj,name,education,inDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Doctor��Ϣ��¼"; 
        String[] headers = { "ҽ�����","���ڿ���","����","�Ա�","ҽ����Ƭ","ѧ��","��Ժ����","��ϵ�绰","ÿ�ճ������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Doctor.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯDoctor��Ϣ*/
    public String FrontQueryDoctor() {
        if(currentPage == 0) currentPage = 1;
        if(doctorNo == null) doctorNo = "";
        if(name == null) name = "";
        if(education == null) education = "";
        if(inDate == null) inDate = "";
        List<Doctor> doctorList = doctorDAO.QueryDoctorInfo(doctorNo, departmentObj, name, education, inDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        doctorDAO.CalculateTotalPageAndRecordNumber(doctorNo, departmentObj, name, education, inDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = doctorDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�Doctor��Ϣ*/
    public String ModifyDoctorQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������doctorNo��ȡDoctor����*/
        Doctor doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("doctor",  doctor);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Doctor��Ϣ*/
    public String FrontShowDoctorQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������doctorNo��ȡDoctor����*/
        Doctor doctor = doctorDAO.GetDoctorByDoctorNo(doctorNo);

        List<Department> departmentList = departmentDAO.QueryAllDepartmentInfo();
        ctx.put("departmentList", departmentList);
        ctx.put("doctor",  doctor);
        return "front_show_view";
    }

    /*�����޸�Doctor��Ϣ*/
    public String ModifyDoctor() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Department departmentObj = departmentDAO.GetDepartmentByDepartmentId(doctor.getDepartmentObj().getDepartmentId());
            doctor.setDepartmentObj(departmentObj);
            /*����ҽ����Ƭ�ϴ�*/
            if(doctorPhotoFile != null) {
            	String doctorPhotoPath = photoUpload(doctorPhotoFile,doctorPhotoFileContentType);
            	doctor.setDoctorPhoto(doctorPhotoPath);
            }
            doctorDAO.UpdateDoctor(doctor);
            ctx.put("message",  java.net.URLEncoder.encode("Doctor��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctor��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Doctor��Ϣ*/
    public String DeleteDoctor() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            doctorDAO.DeleteDoctor(doctorNo);
            ctx.put("message",  java.net.URLEncoder.encode("Doctorɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Doctorɾ��ʧ��!"));
            return "error";
        }
    }

}
