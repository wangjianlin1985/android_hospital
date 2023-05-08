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
import com.chengxusheji.dao.LeaveWordDAO;
import com.chengxusheji.domain.LeaveWord;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeaveWordAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ������*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ظ�ʱ��*/
    private String replyTime;
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
    public String getReplyTime() {
        return this.replyTime;
    }

    /*�������Ҫ��ѯ������: �ظ���ҽ��*/
    private String replyDoctor;
    public void setReplyDoctor(String replyDoctor) {
        this.replyDoctor = replyDoctor;
    }
    public String getReplyDoctor() {
        return this.replyDoctor;
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

    private int leaveWordId;
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }
    public int getLeaveWordId() {
        return leaveWordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource LeaveWordDAO leaveWordDAO;

    /*��������LeaveWord����*/
    private LeaveWord leaveWord;
    public void setLeaveWord(LeaveWord leaveWord) {
        this.leaveWord = leaveWord;
    }
    public LeaveWord getLeaveWord() {
        return this.leaveWord;
    }

    /*��ת�����LeaveWord��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���LeaveWord��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveWord.getUserObj().getUser_name());
            leaveWord.setUserObj(userObj);
            leaveWordDAO.AddLeaveWord(leaveWord);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWord��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWord���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveWord��Ϣ*/
    public String QueryLeaveWord() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title, userObj, replyTime, replyDoctor, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveWordDAO.CalculateTotalPageAndRecordNumber(title, userObj, replyTime, replyDoctor);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveWordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveWordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveWordList",  leaveWordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("replyTime", replyTime);
        ctx.put("replyDoctor", replyDoctor);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryLeaveWordOutputToExcel() { 
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title,userObj,replyTime,replyDoctor);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveWord��Ϣ��¼"; 
        String[] headers = { "����id","����","����ʱ��","������","�ظ�����","�ظ�ʱ��","�ظ���ҽ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveWordList.size();i++) {
        	LeaveWord leaveWord = leaveWordList.get(i); 
        	dataset.add(new String[]{leaveWord.getLeaveWordId() + "",leaveWord.getTitle(),leaveWord.getAddTime(),leaveWord.getUserObj().getName(),
leaveWord.getReplyContent(),leaveWord.getReplyTime(),leaveWord.getReplyDoctor()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveWord.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLeaveWord��Ϣ*/
    public String FrontQueryLeaveWord() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title, userObj, replyTime, replyDoctor, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveWordDAO.CalculateTotalPageAndRecordNumber(title, userObj, replyTime, replyDoctor);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveWordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveWordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveWordList",  leaveWordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("title", title);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("replyTime", replyTime);
        ctx.put("replyDoctor", replyDoctor);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�LeaveWord��Ϣ*/
    public String ModifyLeaveWordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveWordId��ȡLeaveWord����*/
        LeaveWord leaveWord = leaveWordDAO.GetLeaveWordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveWord",  leaveWord);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LeaveWord��Ϣ*/
    public String FrontShowLeaveWordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveWordId��ȡLeaveWord����*/
        LeaveWord leaveWord = leaveWordDAO.GetLeaveWordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveWord",  leaveWord);
        return "front_show_view";
    }

    /*�����޸�LeaveWord��Ϣ*/
    public String ModifyLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveWord.getUserObj().getUser_name());
            leaveWord.setUserObj(userObj);
            leaveWordDAO.UpdateLeaveWord(leaveWord);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWord��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWord��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LeaveWord��Ϣ*/
    public String DeleteLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveWordDAO.DeleteLeaveWord(leaveWordId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWordɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWordɾ��ʧ��!"));
            return "error";
        }
    }

}
