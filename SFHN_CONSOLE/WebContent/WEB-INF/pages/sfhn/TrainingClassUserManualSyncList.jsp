<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.console.config.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<TrainingClassUserBean> list = (List<TrainingClassUserBean>)request.getAttribute("TrainingClassUserList");
	Map<Integer, FarmerInfoBean> farmerInfoMap = (Map<Integer, FarmerInfoBean>)request.getAttribute("farmerInfoMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学员手动同步</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">学员手动同步</p>
<form name="formSearch" method="post" action="TrainingClassUserManualSyncList.do">
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>姓名</th>
      <th>班级ID</th>
      <th>身份证号</th>
      <th>同步状态</th>
      <th>操作</th>
    </tr>
    <%
    for(TrainingClassUserBean bean:list){
    	FarmerInfoBean farmerInfoBean = farmerInfoMap.get(bean.getDeclareId());
    	if(farmerInfoBean==null) farmerInfoBean = new FarmerInfoBean();
    %> 
    <tr>
      <td><%=farmerInfoBean.getUserName() %></td>
      <td><%=bean.getClassId() %></td>
      <td><%=farmerInfoBean.getIdentityNumber() %></td>
      <td>未同步</td>
      <td align="center">
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %> 
       <a href="/sfhn/TrainingClassUserManualSync.do?declareId=<%=bean.getDeclareId()%>&classId=<%=bean.getClassId()%>">同步</a>
       <%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>