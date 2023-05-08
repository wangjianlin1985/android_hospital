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

    /*界面层需要查询的属性: 标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 留言人*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 回复时间*/
    private String replyTime;
    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
    public String getReplyTime() {
        return this.replyTime;
    }

    /*界面层需要查询的属性: 回复的医生*/
    private String replyDoctor;
    public void setReplyDoctor(String replyDoctor) {
        this.replyDoctor = replyDoctor;
    }
    public String getReplyDoctor() {
        return this.replyDoctor;
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

    private int leaveWordId;
    public void setLeaveWordId(int leaveWordId) {
        this.leaveWordId = leaveWordId;
    }
    public int getLeaveWordId() {
        return leaveWordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource LeaveWordDAO leaveWordDAO;

    /*待操作的LeaveWord对象*/
    private LeaveWord leaveWord;
    public void setLeaveWord(LeaveWord leaveWord) {
        this.leaveWord = leaveWord;
    }
    public LeaveWord getLeaveWord() {
        return this.leaveWord;
    }

    /*跳转到添加LeaveWord视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加LeaveWord信息*/
    @SuppressWarnings("deprecation")
    public String AddLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveWord.getUserObj().getUser_name());
            leaveWord.setUserObj(userObj);
            leaveWordDAO.AddLeaveWord(leaveWord);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWord添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWord添加失败!"));
            return "error";
        }
    }

    /*查询LeaveWord信息*/
    public String QueryLeaveWord() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title, userObj, replyTime, replyDoctor, currentPage);
        /*计算总的页数和总的记录数*/
        leaveWordDAO.CalculateTotalPageAndRecordNumber(title, userObj, replyTime, replyDoctor);
        /*获取到总的页码数目*/
        totalPage = leaveWordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryLeaveWordOutputToExcel() { 
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title,userObj,replyTime,replyDoctor);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveWord信息记录"; 
        String[] headers = { "留言id","标题","留言时间","留言人","回复内容","回复时间","回复的医生"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LeaveWord.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LeaveWord信息*/
    public String FrontQueryLeaveWord() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(replyTime == null) replyTime = "";
        if(replyDoctor == null) replyDoctor = "";
        List<LeaveWord> leaveWordList = leaveWordDAO.QueryLeaveWordInfo(title, userObj, replyTime, replyDoctor, currentPage);
        /*计算总的页数和总的记录数*/
        leaveWordDAO.CalculateTotalPageAndRecordNumber(title, userObj, replyTime, replyDoctor);
        /*获取到总的页码数目*/
        totalPage = leaveWordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的LeaveWord信息*/
    public String ModifyLeaveWordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveWordId获取LeaveWord对象*/
        LeaveWord leaveWord = leaveWordDAO.GetLeaveWordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveWord",  leaveWord);
        return "modify_view";
    }

    /*查询要修改的LeaveWord信息*/
    public String FrontShowLeaveWordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveWordId获取LeaveWord对象*/
        LeaveWord leaveWord = leaveWordDAO.GetLeaveWordByLeaveWordId(leaveWordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("leaveWord",  leaveWord);
        return "front_show_view";
    }

    /*更新修改LeaveWord信息*/
    public String ModifyLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(leaveWord.getUserObj().getUser_name());
            leaveWord.setUserObj(userObj);
            leaveWordDAO.UpdateLeaveWord(leaveWord);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWord信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWord信息更新失败!"));
            return "error";
       }
   }

    /*删除LeaveWord信息*/
    public String DeleteLeaveWord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveWordDAO.DeleteLeaveWord(leaveWordId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveWord删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveWord删除失败!"));
            return "error";
        }
    }

}
