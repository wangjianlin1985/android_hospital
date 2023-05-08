<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Doctor" %>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Department��Ϣ
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    Doctor doctor = (Doctor)request.getAttribute("doctor");

%>
<HTML><HEAD><TITLE>�鿴ҽ��</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>ҽ�����:</td>
    <td width=70%><%=doctor.getDoctorNo() %></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><%=doctor.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>���ڿ���:</td>
    <td width=70%>
      <%=doctor.getDepartmentObj().getDepartmentName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><%=doctor.getName() %></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><%=doctor.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>ҽ����Ƭ:</td>
    <td width=70%><img src="<%=basePath %><%=doctor.getDoctorPhoto() %>" width="200px" border="0px"/></td>
  </tr>
  <tr>
    <td width=30%>ѧ��:</td>
    <td width=70%><%=doctor.getEducation() %></td>
  </tr>

  <tr>
    <td width=30%>��Ժ����:</td>
        <% java.text.DateFormat inDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=inDateSDF.format(doctor.getInDate()) %></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><%=doctor.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>ÿ�ճ������:</td>
    <td width=70%><%=doctor.getVisiteTimes() %></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><%=doctor.getMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
