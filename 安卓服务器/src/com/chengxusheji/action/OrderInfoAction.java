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

    /*�������Ҫ��ѯ������: ԤԼ�û�*/
    private UserInfo uesrObj;
    public void setUesrObj(UserInfo uesrObj) {
        this.uesrObj = uesrObj;
    }
    public UserInfo getUesrObj() {
        return this.uesrObj;
    }

    /*�������Ҫ��ѯ������: ԤԼҽ��*/
    private Doctor doctor;
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
    public Doctor getDoctor() {
        return this.doctor;
    }

    /*�������Ҫ��ѯ������: ԤԼ����*/
    private String orderDate;
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderDate() {
        return this.orderDate;
    }

    /*�������Ҫ��ѯ������: ԤԼʱ���*/
    private TimeSlot timeSlotObj;
    public void setTimeSlotObj(TimeSlot timeSlotObj) {
        this.timeSlotObj = timeSlotObj;
    }
    public TimeSlot getTimeSlotObj() {
        return this.timeSlotObj;
    }

    /*�������Ҫ��ѯ������: ����״̬*/
    private VisitState visiteStateObj;
    public void setVisiteStateObj(VisitState visiteStateObj) {
        this.visiteStateObj = visiteStateObj;
    }
    public VisitState getVisiteStateObj() {
        return this.visiteStateObj;
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

    private int orderId;
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getOrderId() {
        return orderId;
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
    @Resource DoctorDAO doctorDAO;
    @Resource TimeSlotDAO timeSlotDAO;
    @Resource VisitStateDAO visitStateDAO;
    @Resource OrderInfoDAO orderInfoDAO;

    /*��������OrderInfo����*/
    private OrderInfo orderInfo;
    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
    public OrderInfo getOrderInfo() {
        return this.orderInfo;
    }

    /*��ת�����OrderInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�Doctor��Ϣ*/
        List<Doctor> doctorList = doctorDAO.QueryAllDoctorInfo();
        ctx.put("doctorList", doctorList);
        /*��ѯ���е�TimeSlot��Ϣ*/
        List<TimeSlot> timeSlotList = timeSlotDAO.QueryAllTimeSlotInfo();
        ctx.put("timeSlotList", timeSlotList);
        /*��ѯ���е�VisitState��Ϣ*/
        List<VisitState> visitStateList = visitStateDAO.QueryAllVisitStateInfo();
        ctx.put("visitStateList", visitStateList);
        return "add_view";
    }

    /*���OrderInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯOrderInfo��Ϣ*/
    public String QueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryOrderInfoOutputToExcel() { 
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj,doctor,orderDate,timeSlotObj,visiteStateObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "OrderInfo��Ϣ��¼"; 
        String[] headers = { "ԤԼid","ԤԼ�û�","ԤԼҽ��","ԤԼ����","ԤԼʱ���","����״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"OrderInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯOrderInfo��Ϣ*/
    public String FrontQueryOrderInfo() {
        if(currentPage == 0) currentPage = 1;
        if(orderDate == null) orderDate = "";
        List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfoInfo(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        orderInfoDAO.CalculateTotalPageAndRecordNumber(uesrObj, doctor, orderDate, timeSlotObj, visiteStateObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = orderInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String ModifyOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
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

    /*��ѯҪ�޸ĵ�OrderInfo��Ϣ*/
    public String FrontShowOrderInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������orderId��ȡOrderInfo����*/
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

    /*�����޸�OrderInfo��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��OrderInfo��Ϣ*/
    public String DeleteOrderInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            orderInfoDAO.DeleteOrderInfo(orderId);
            ctx.put("message",  java.net.URLEncoder.encode("OrderInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("OrderInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
