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
import com.chengxusheji.dao.OrderInfoDAO;
import com.chengxusheji.domain.OrderInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.DoctorDAO;
import com.chengxusheji.domain.Doctor;
import com.chengxusheji.dao.TimeSlotDAO;
import com.chengxusheji.domain.TimeSlot;
import com.chengxusheji.dao.VisitStateDAO;
import com.chengxusheji.domain.VisitState;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class OrderInfoAction extends BaseAction {

    /*界面层需要查询的属性: 预约用户*/
    private UserInfo uesrObj;
    public void setUesrObj(UserInfo uesrObj) {
        this.uesrObj = uesrObj;
    }
    public UserInfo getUesrObj() {
        return this.uesrObj;
    }

    /*界面层需要查询的属性: 预约医生*/
    private Doctor doctor;
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Doctor getDoctor() {
        return this.doctor;
    }

    /*界面层需要查询的属性: 预约日期*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*界面层需要查询的属性: 预约时间段*/
    private TimeSlot timeSlotObj;
    public void setTimeSlotObj(TimeSlot timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }
    public TimeSlot getTimeSlotObj() {
        return this.timeSlotObj;
    }

    /*界面层需要查询的属性: 出诊状态*/
    private VisitState visiteStateObj;
    public void setVisiteStateObj(VisitState visiteStateObj) {
        this.visiteStateObj = visiteStateObj;
    }
    public VisitState getVisiteStateObj() {
        return this.visiteStateObj;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource DoctorDAO doctorDAO;
    @Resource TimeSlotDAO timeSlotDAO;
    @Resource VisitStateDAO visitStateDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*待操作的OrderInfo对象*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*跳转到添加OrderInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的Doctor信息*/
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        /*查询所有的TimeSlot信息*/
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        /*查询所有的VisitState信息*/
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        return "add_view";
    }

    /*添加OrderInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo uesrObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUesrObj().getUser_name());
            orderInfo.setUesrObj(uesrObj);
            Doctor doctor = doctorDAO.GetDoctorByDoctorNo(orderInfo.getDoctor().getDoctorNo());
            orderInfo.setDoctor(doctor);
            TimeSlot timeSlotObj = timeSlotDAO.GetTimeSlotByTimeSlotId(orderInfo.getTimeSlotObj().getTimeSlotId());
            orderInfo.setTimeSlotObj(timeSlotObj);
            VisitState visiteStateObj = visitStateDAO.GetVisitStateByVisitStateId(orderInfo.getVisiteStateObj().getVisitStateId());
            orderInfo.setVisiteStateObj(visiteStateObj);
            orderInfoDAO.AddOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo添加失败!"));
            return "error";
        }
    }

    /*查询OrderInfo信息*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("uesrObj", uesrObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("doctor", doctor);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("orderDate", orderDate);
        ctx.put("timeSlotObj", timeSlotObj);
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        ctx.put("visiteStateObj", visiteStateObj);
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj,doctor,orderDate,timeSlotObj,visiteStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo信息记录"; 
        String[] headers = { "预约id","预约用户","预约医生","预约日期","预约时间段","出诊状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<orderInfoList.size();i++) {
        	OrderInfo orderInfo = orderInfoList.get(i); 
        	dataset.add(new String[]{orderInfo.getOrderId() + "",orderInfo.getUesrObj().getName(),
orderInfo.getDoctor().getName(),
new SimpleDateFormat("yyyy-MM-dd").format(orderInfo.getOrderDate()),orderInfo.getTimeSlotObj().getTimeSlotName(),
orderInfo.getVisiteStateObj().getVisitStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询OrderInfo信息*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj, currentPage);
        /*计算总的页数和总的记录数*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj);
        /*获取到总的页码数目*/
        totalPage = orderInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = orderInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("orderInfoList",  orderInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("uesrObj", uesrObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("doctor", doctor);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        ctx.put("orderDate", orderDate);
        ctx.put("timeSlotObj", timeSlotObj);
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        ctx.put("visiteStateObj", visiteStateObj);
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        return "front_query_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        ctx.put("orderInfo",  orderInfo);
        return "modify_view";
    }

    /*查询要修改的OrderInfo信息*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键orderId获取OrderInfo对象*/
        OrderInfo orderInfo = orderInfoDAO.GetOrderInfoByOrderId(orderId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        ctx.put("orderInfo",  orderInfo);
        return "front_show_view";
    }

    /*更新修改OrderInfo信息*/
    public String ModifyOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo uesrObj = userInfoDAO.GetUserInfoByUser_name(orderInfo.getUesrObj().getUser_name());
            orderInfo.setUesrObj(uesrObj);
            Doctor doctor = doctorDAO.GetDoctorByDoctorNo(orderInfo.getDoctor().getDoctorNo());
            orderInfo.setDoctor(doctor);
            TimeSlot timeSlotObj = timeSlotDAO.GetTimeSlotByTimeSlotId(orderInfo.getTimeSlotObj().getTimeSlotId());
            orderInfo.setTimeSlotObj(timeSlotObj);
            VisitState visiteStateObj = visitStateDAO.GetVisitStateByVisitStateId(orderInfo.getVisiteStateObj().getVisitStateId());
            orderInfo.setVisiteStateObj(visiteStateObj);
            orderInfoDAO.UpdateOrderInfo(orderInfo);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除OrderInfo信息*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo删除失败!"));
            return "error";
        }
    }

}
