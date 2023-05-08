<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改用户</TITLE>
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
/*验证表单*/
function checkForm() {
    var user_name = document.getElementById("userInfo.user_name").value;
    if(user_name=="") {
        alert('请输入用户名!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('请输入姓名!');
        return false;
    }
    var sex = document.getElementById("userInfo.sex").value;
    if(sex=="") {
        alert('请输入性别!');
        return false;
    }
    var jiguan = document.getElementById("userInfo.jiguan").value;
    if(jiguan=="") {
        alert('请输入籍贯!');
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
    <TD align="left" vAlign=top ><s:form action="UserInfo/UserInfo_ModifyUserInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>用户名:</td>
    <td width=70%><input id="userInfo.user_name" name="userInfo.user_name" type="text" value="<%=userInfo.getUser_name() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="20" value='<%=userInfo.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" value='<%=userInfo.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="userInfo.sex" name="userInfo.sex" type="text" size="4" value='<%=userInfo.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>用户照片:</td>
    <td width=70%><img src="<%=basePath %><%=userInfo.getUserPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="userInfo.userPhoto" value="<%=userInfo.getUserPhoto() %>" />
    <input id="userPhotoFile" name="userPhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>出生日期:</td>
    <% DateFormat birthdaySDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="userInfo.birthday"  name="userInfo.birthday" onclick="setDay(this);" value='<%=birthdaySDF.format(userInfo.getBirthday()) %>'/></td>
  </tr>

  <tr>
    <td width=30%>籍贯:</td>
    <td width=70%><input id="userInfo.jiguan" name="userInfo.jiguan" type="text" size="20" value='<%=userInfo.getJiguan() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" value='<%=userInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="90" value='<%=userInfo.getAddress() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
