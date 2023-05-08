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
import com.chengxusheji.dao.DepartmentDAO;
import com.chengxusheji.domain.Department;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class DepartmentAction extends BaseAction {

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

    private int departmentId;
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    public int getDepartmentId() {
        return departmentId;
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

    /*��������Department����*/
    private Department department;
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return this.department;
    }

    /*��ת�����Department��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Department��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            departmentDAO.AddDepartment(department);
            ctx.put("message",  java.net.URLEncoder.encode("Department��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Department���ʧ��!"));
            return "error";
        }
    }

    /*��ѯDepartment��Ϣ*/
    public String QueryDepartment() {
        if(currentPage == 0) currentPage = 1;
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        departmentDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = departmentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = departmentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("departmentList",  departmentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryDepartmentOutputToExcel() { 
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Department��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<departmentList.size();i++) {
        	Department department = departmentList.get(i); 
        	dataset.add(new String[]{department.getDepartmentId() + "",department.getDepartmentName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Department.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯDepartment��Ϣ*/
    public String FrontQueryDepartment() {
        if(currentPage == 0) currentPage = 1;
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        departmentDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = departmentDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = departmentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("departmentList",  departmentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Department��Ϣ*/
    public String ModifyDepartmentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������departmentId��ȡDepartment����*/
        Department department = departmentDAO.GetDepartmentByDepartmentId(departmentId);

        ctx.put("department",  department);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Department��Ϣ*/
    public String FrontShowDepartmentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������departmentId��ȡDepartment����*/
        Department department = departmentDAO.GetDepartmentByDepartmentId(departmentId);

        ctx.put("department",  department);
        return "front_show_view";
    }

    /*�����޸�Department��Ϣ*/
    public String ModifyDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            departmentDAO.UpdateDepartment(department);
            ctx.put("message",  java.net.URLEncoder.encode("Department��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Department��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Department��Ϣ*/
    public String DeleteDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            departmentDAO.DeleteDepartment(departmentId);
            ctx.put("message",  java.net.URLEncoder.encode("Departmentɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Departmentɾ��ʧ��!"));
            return "error";
        }
    }

}
