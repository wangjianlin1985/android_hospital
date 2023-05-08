<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.LeaveWord" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    LeaveWord leaveWord = (LeaveWord)request.getAttribute("leaveWord");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�����</TITLE>
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
    var title = document.getElementById("leaveWord.title").value;
    if(title=="") {
        alert('���������!');
        return false;
    }
    var content = document.getElementById("leaveWord.content").value;
    if(content=="") {
        alert('��������������!');
        return false;
    }
    var addTime = document.getElementById("leaveWord.addTime").value;
    if(addTime=="") {
        alert('����������ʱ��!');
        return false;
    }
    var replyTime = document.getElementById("leaveWord.replyTime").value;
    if(replyTime=="") {
        alert('������ظ�ʱ��!');
        return false;
    }
    var replyDoctor = document.getElementById("leaveWord.replyDoctor").value;
    if(replyDoctor=="") {
        alert('������ظ���ҽ��!');
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
    <TD align="left" vAlign=top ><s:form action="LeaveWord/LeaveWord_ModifyLeaveWord.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>����id:</td>
    <td width=70%><input id="leaveWord.leaveWordId" name="leaveWord.leaveWordId" type="text" value="<%=leaveWord.getLeaveWordId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%><input id="leaveWord.title" name="leaveWord.title" type="text" size="80" value='<%=leaveWord.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="leaveWord.content" name="leaveWord.content" rows=5 cols=50><%=leaveWord.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="leaveWord.addTime" name="leaveWord.addTime" type="text" size="20" value='<%=leaveWord.getAddTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <select name="leaveWord.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(leaveWord.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ظ�����:</td>
    <td width=70%><textarea id="leaveWord.replyContent" name="leaveWord.replyContent" rows=5 cols=50><%=leaveWord.getReplyContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�ظ�ʱ��:</td>
    <td width=70%><input id="leaveWord.replyTime" name="leaveWord.replyTime" type="text" size="20" value='<%=leaveWord.getReplyTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ظ���ҽ��:</td>
    <td width=70%><input id="leaveWord.replyDoctor" name="leaveWord.replyDoctor" type="text" size="20" value='<%=leaveWord.getReplyDoctor() %>'/></td>
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
