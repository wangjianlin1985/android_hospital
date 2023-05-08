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
import com.chengxusheji.dao.VisitStateDAO;
import com.chengxusheji.domain.VisitState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class VisitStateAction extends BaseAction {

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

    private int visitStateId;
    public void setVisitStateId(int visitStateId) {
        this.visitStateId = visitStateId;
    }
    public int getVisitStateId() {
        return visitStateId;
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
    @Resource VisitStateDAO visitStateDAO;

    /*待操作的VisitState对象*/
    private VisitState visitState;
    public void setVisitState(VisitState visitState) {
        this.visitState = visitState;
    }
    public VisitState getVisitState() {
        return this.visitState;
    }

    /*跳转到添加VisitState视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加VisitState信息*/
    @SuppressWarnings("deprecation")
    public String AddVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            visitStateDAO.AddVisitState(visitState);
            ctx.put("message",  java.net.URLEncoder.encode("VisitState添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitState添加失败!"));
            return "error";
        }
    }

    /*查询VisitState信息*/
    public String QueryVisitState() {
        if(currentPage == 0) currentPage = 1;
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        visitStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = visitStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = visitStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("visitStateList",  visitStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryVisitStateOutputToExcel() { 
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "VisitState信息记录"; 
        String[] headers = { "状态id","出诊状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<visitStateList.size();i++) {
        	VisitState visitState = visitStateList.get(i); 
        	dataset.add(new String[]{visitState.getVisitStateId() + "",visitState.getVisitStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"VisitState.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询VisitState信息*/
    public String FrontQueryVisitState() {
        if(currentPage == 0) currentPage = 1;
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        visitStateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = visitStateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = visitStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("visitStateList",  visitStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的VisitState信息*/
    public String ModifyVisitStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键visitStateId获取VisitState对象*/
        VisitState visitState = visitStateDAO.GetVisitStateByVisitStateId(visitStateId);

        ctx.put("visitState",  visitState);
        return "modify_view";
    }

    /*查询要修改的VisitState信息*/
    public String FrontShowVisitStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键visitStateId获取VisitState对象*/
        VisitState visitState = visitStateDAO.GetVisitStateByVisitStateId(visitStateId);

        ctx.put("visitState",  visitState);
        return "front_show_view";
    }

    /*更新修改VisitState信息*/
    public String ModifyVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            visitStateDAO.UpdateVisitState(visitState);
            ctx.put("message",  java.net.URLEncoder.encode("VisitState信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitState信息更新失败!"));
            return "error";
       }
   }

    /*删除VisitState信息*/
    public String DeleteVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            visitStateDAO.DeleteVisitState(visitStateId);
            ctx.put("message",  java.net.URLEncoder.encode("VisitState删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitState删除失败!"));
            return "error";
        }
    }

}
