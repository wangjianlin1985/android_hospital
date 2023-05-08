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

    private int departmentId;
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    public int getDepartmentId() {
        return departmentId;
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

    /*待操作的Department对象*/
    private Department department;
    public void setDepartment(Department department) {
        this.department = department;
    }
    public Department getDepartment() {
        return this.department;
    }

    /*跳转到添加Department视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Department信息*/
    @SuppressWarnings("deprecation")
    public String AddDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            departmentDAO.AddDepartment(department);
            ctx.put("message",  java.net.URLEncoder.encode("Department添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Department添加失败!"));
            return "error";
        }
    }

    /*查询Department信息*/
    public String QueryDepartment() {
        if(currentPage == 0) currentPage = 1;
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo(currentPage);
        /*计算总的页数和总的记录数*/
        departmentDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = departmentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = departmentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("departmentList",  departmentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryDepartmentOutputToExcel() { 
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Department信息记录"; 
        String[] headers = { "科室id","科室名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Department.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Department信息*/
    public String FrontQueryDepartment() {
        if(currentPage == 0) currentPage = 1;
        List<Department> departmentList = departmentDAO.QueryDepartmentInfo(currentPage);
        /*计算总的页数和总的记录数*/
        departmentDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = departmentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = departmentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("departmentList",  departmentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的Department信息*/
    public String ModifyDepartmentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键departmentId获取Department对象*/
        Department department = departmentDAO.GetDepartmentByDepartmentId(departmentId);

        ctx.put("department",  department);
        return "modify_view";
    }

    /*查询要修改的Department信息*/
    public String FrontShowDepartmentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键departmentId获取Department对象*/
        Department department = departmentDAO.GetDepartmentByDepartmentId(departmentId);

        ctx.put("department",  department);
        return "front_show_view";
    }

    /*更新修改Department信息*/
    public String ModifyDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try {
            departmentDAO.UpdateDepartment(department);
            ctx.put("message",  java.net.URLEncoder.encode("Department信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Department信息更新失败!"));
            return "error";
       }
   }

    /*删除Department信息*/
    public String DeleteDepartment() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            departmentDAO.DeleteDepartment(departmentId);
            ctx.put("message",  java.net.URLEncoder.encode("Department删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Department删除失败!"));
            return "error";
        }
    }

}
