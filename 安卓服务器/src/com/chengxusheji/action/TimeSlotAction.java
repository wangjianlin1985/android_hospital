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
import com.chengxusheji.dao.TimeSlotDAO;
import com.chengxusheji.domain.TimeSlot;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TimeSlotAction extends BaseAction {

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

    private int timeSlotId;
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
    public int getTimeSlotId() {
        return timeSlotId;
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
    @Resource TimeSlotDAO timeSlotDAO;

    /*待操作的TimeSlot对象*/
    private TimeSlot timeSlot;
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    /*跳转到添加TimeSlot视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加TimeSlot信息*/
    @SuppressWarnings("deprecation")
    public String AddTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSlotDAO.AddTimeSlot(timeSlot);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlot添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlot添加失败!"));
            return "error";
        }
    }

    /*查询TimeSlot信息*/
    public String QueryTimeSlot() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSlotDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSlotDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSlotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSlotList",  timeSlotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryTimeSlotOutputToExcel() { 
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSlot信息记录"; 
        String[] headers = { "时间段id","时间段名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<timeSlotList.size();i++) {
        	TimeSlot timeSlot = timeSlotList.get(i); 
        	dataset.add(new String[]{timeSlot.getTimeSlotId() + "",timeSlot.getTimeSlotName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"TimeSlot.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询TimeSlot信息*/
    public String FrontQueryTimeSlot() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSlotDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSlotDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSlotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSlotList",  timeSlotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的TimeSlot信息*/
    public String ModifyTimeSlotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键timeSlotId获取TimeSlot对象*/
        TimeSlot timeSlot = timeSlotDAO.GetTimeSlotByTimeSlotId(timeSlotId);

        ctx.put("timeSlot",  timeSlot);
        return "modify_view";
    }

    /*查询要修改的TimeSlot信息*/
    public String FrontShowTimeSlotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键timeSlotId获取TimeSlot对象*/
        TimeSlot timeSlot = timeSlotDAO.GetTimeSlotByTimeSlotId(timeSlotId);

        ctx.put("timeSlot",  timeSlot);
        return "front_show_view";
    }

    /*更新修改TimeSlot信息*/
    public String ModifyTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSlotDAO.UpdateTimeSlot(timeSlot);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlot信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlot信息更新失败!"));
            return "error";
       }
   }

    /*删除TimeSlot信息*/
    public String DeleteTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSlotDAO.DeleteTimeSlot(timeSlotId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlot删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlot删除失败!"));
            return "error";
        }
    }

}
