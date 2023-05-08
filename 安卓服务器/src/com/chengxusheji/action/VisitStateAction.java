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

    private int visitStateId;
    public void setVisitStateId(int visitStateId) {
        this.visitStateId = visitStateId;
    }
    public int getVisitStateId() {
        return visitStateId;
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
    @Resource VisitStateDAO visitStateDAO;

    /*��������VisitState����*/
    private VisitState visitState;
    public void setVisitState(VisitState visitState) {
        this.visitState = visitState;
    }
    public VisitState getVisitState() {
        return this.visitState;
    }

    /*��ת�����VisitState��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���VisitState��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            visitStateDAO.AddVisitState(visitState);
            ctx.put("message",  java.net.URLEncoder.encode("VisitState��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitState���ʧ��!"));
            return "error";
        }
    }

    /*��ѯVisitState��Ϣ*/
    public String QueryVisitState() {
        if(currentPage == 0) currentPage = 1;
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        visitStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = visitStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = visitStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("visitStateList",  visitStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryVisitStateOutputToExcel() { 
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "VisitState��Ϣ��¼"; 
        String[] headers = { "״̬id","����״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"VisitState.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯVisitState��Ϣ*/
    public String FrontQueryVisitState() {
        if(currentPage == 0) currentPage = 1;
        List<VisitState> visitStateList = visitStateDAO.QueryVisitStateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        visitStateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = visitStateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = visitStateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("visitStateList",  visitStateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�VisitState��Ϣ*/
    public String ModifyVisitStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������visitStateId��ȡVisitState����*/
        VisitState visitState = visitStateDAO.GetVisitStateByVisitStateId(visitStateId);

        ctx.put("visitState",  visitState);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�VisitState��Ϣ*/
    public String FrontShowVisitStateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������visitStateId��ȡVisitState����*/
        VisitState visitState = visitStateDAO.GetVisitStateByVisitStateId(visitStateId);

        ctx.put("visitState",  visitState);
        return "front_show_view";
    }

    /*�����޸�VisitState��Ϣ*/
    public String ModifyVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try {
            visitStateDAO.UpdateVisitState(visitState);
            ctx.put("message",  java.net.URLEncoder.encode("VisitState��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitState��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��VisitState��Ϣ*/
    public String DeleteVisitState() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            visitStateDAO.DeleteVisitState(visitStateId);
            ctx.put("message",  java.net.URLEncoder.encode("VisitStateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("VisitStateɾ��ʧ��!"));
            return "error";
        }
    }

}
