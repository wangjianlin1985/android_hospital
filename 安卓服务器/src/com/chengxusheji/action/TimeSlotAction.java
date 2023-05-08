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

    private int timeSlotId;
    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
    public int getTimeSlotId() {
        return timeSlotId;
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
    @Resource TimeSlotDAO timeSlotDAO;

    /*��������TimeSlot����*/
    private TimeSlot timeSlot;
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    /*��ת�����TimeSlot��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���TimeSlot��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSlotDAO.AddTimeSlot(timeSlot);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlot��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlot���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTimeSlot��Ϣ*/
    public String QueryTimeSlot() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSlotDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSlotDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSlotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSlotList",  timeSlotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTimeSlotOutputToExcel() { 
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSlot��Ϣ��¼"; 
        String[] headers = { "ʱ���id","ʱ�������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TimeSlot.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTimeSlot��Ϣ*/
    public String FrontQueryTimeSlot() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryTimeSlotInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSlotDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSlotDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSlotDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSlotList",  timeSlotList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�TimeSlot��Ϣ*/
    public String ModifyTimeSlotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������timeSlotId��ȡTimeSlot����*/
        TimeSlot timeSlot = timeSlotDAO.GetTimeSlotByTimeSlotId(timeSlotId);

        ctx.put("timeSlot",  timeSlot);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�TimeSlot��Ϣ*/
    public String FrontShowTimeSlotQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������timeSlotId��ȡTimeSlot����*/
        TimeSlot timeSlot = timeSlotDAO.GetTimeSlotByTimeSlotId(timeSlotId);

        ctx.put("timeSlot",  timeSlot);
        return "front_show_view";
    }

    /*�����޸�TimeSlot��Ϣ*/
    public String ModifyTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSlotDAO.UpdateTimeSlot(timeSlot);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlot��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlot��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��TimeSlot��Ϣ*/
    public String DeleteTimeSlot() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSlotDAO.DeleteTimeSlot(timeSlotId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSlotɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSlotɾ��ʧ��!"));
            return "error";
        }
    }

}
