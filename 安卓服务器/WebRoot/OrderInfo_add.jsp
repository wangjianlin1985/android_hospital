<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Doctor" %>
<%@ page import="com.chengxusheji.domain.TimeSlot" %>
<%@ page import="com.chengxusheji.domain.VisitState" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�Doctor��Ϣ
    List<Doctor> doctorList = (List<Doctor>)request.getAttribute("doctorList");
    //��ȡ���е�TimeSlot��Ϣ
    List<TimeSlot> timeSlotList = (List<TimeSlot>)request.getAttribute("timeSlotList");
    //��ȡ���е�VisitState��Ϣ
    List<VisitState> visitStateList = (List<VisitState>)request.getAttribute("visitStateList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���ԤԼ</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="OrderInfo/OrderInfo_AddOrderInfo.action" method="post" id="orderInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>ԤԼ�û�:</td>
    <td width=70%>
      <select name="orderInfo.uesrObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getUser_name() %>'><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ԤԼҽ��:</td>
    <td width=70%>
      <select name="orderInfo.doctor.doctorNo">
      <%
        for(Doctor doctor:doctorList) {
      %>
          <option value='<%=doctor.getDoctorNo() %>'><%=doctor.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ԤԼ����:</td>
    <td width=70%><input type="text" readonly id="orderInfo.orderDate"  name="orderInfo.orderDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>ԤԼʱ���:</td>
    <td width=70%>
      <select name="orderInfo.timeSlotObj.timeSlotId">
      <%
        for(TimeSlot timeSlot:timeSlotList) {
      %>
          <option value='<%=timeSlot.getTimeSlotId() %>'><%=timeSlot.getTimeSlotName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����״̬:</td>
    <td width=70%>
      <select name="orderInfo.visiteStateObj.visitStateId">
      <%
        for(VisitState visitState:visitStateList) {
      %>
          <option value='<%=visitState.getVisitStateId() %>'><%=visitState.getVisitStateName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>ҽ��˵��:</td>
    <td width=70%><input id="orderInfo.introduce" name="orderInfo.introduce" type="text" size="60" /></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
