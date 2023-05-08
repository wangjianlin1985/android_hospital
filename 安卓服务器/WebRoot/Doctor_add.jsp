<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Department" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Department��Ϣ
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>���ҽ��</TITLE> 
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
    var doctorNo = document.getElementById("doctor.doctorNo").value;
    if(doctorNo=="") {
        alert('������ҽ�����!');
        return false;
    }
    var password = document.getElementById("doctor.password").value;
    if(password=="") {
        alert('�������¼����!');
        return false;
    }
    var name = document.getElementById("doctor.name").value;
    if(name=="") {
        alert('����������!');
        return false;
    }
    var sex = document.getElementById("doctor.sex").value;
    if(sex=="") {
        alert('�������Ա�!');
        return false;
    }
    var education = document.getElementById("doctor.education").value;
    if(education=="") {
        alert('������ѧ��!');
        return false;
    }
    var telephone = document.getElementById("doctor.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
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
    <s:form action="Doctor/Doctor_AddDoctor.action" method="post" id="doctorAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>ҽ�����:</td>
    <td width=70%><input id="doctor.doctorNo" name="doctor.doctorNo" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��¼����:</td>
    <td width=70%><input id="doctor.password" name="doctor.password" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���ڿ���:</td>
    <td width=70%>
      <select name="doctor.departmentObj.departmentId">
      <%
        for(Department department:departmentList) {
      %>
          <option value='<%=department.getDepartmentId() %>'><%=department.getDepartmentName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="doctor.name" name="doctor.name" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>�Ա�:</td>
    <td width=70%><input id="doctor.sex" name="doctor.sex" type="text" size="4" /></td>
  </tr>

  <tr>
    <td width=30%>ҽ����Ƭ:</td>
    <td width=70%><input id="doctorPhotoFile" name="doctorPhotoFile" type="file" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>ѧ��:</td>
    <td width=70%><input id="doctor.education" name="doctor.education" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>��Ժ����:</td>
    <td width=70%><input type="text" readonly id="doctor.inDate"  name="doctor.inDate" onclick="setDay(this);"/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="doctor.telephone" name="doctor.telephone" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>ÿ�ճ������:</td>
    <td width=70%><input id="doctor.visiteTimes" name="doctor.visiteTimes" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="doctor.memo" name="doctor.memo" rows="5" cols="50"></textarea></td>
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
