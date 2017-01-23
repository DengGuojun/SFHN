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
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.console.config.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<TrackingServiceInfoBean> list = (List<TrackingServiceInfoBean>)request.getAttribute("TrackingServiceInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int trackingId = (Integer)request.getAttribute("TrackingId");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>跟踪服务列表管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">跟踪服务列表</p>
<form name="formSearch" method="post" action="ExpertInfoList.do">
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
    	  <th>跟踪服务ID</th>  
    	  <th>跟踪服务类型</th>  
      <th>跟踪服务信息标题</th>  
      <th>操作</th>
    </tr>
    <%
    for(TrackingServiceInfoBean bean:list){%> 
    <tr>
    	  <td><%=bean.getServiceId() %></td>
    	  <td><%=bean.getServiceType() %></td>
      <td><%=bean.getTrackingTitle() %></td>
      <td align="center">
      	<a href="/sfhn/TrackingServiceInfoManage.do?serviceId=<%=bean.getServiceId()%>&readOnly=true">查看</a>
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %> 
        | <a href="/sfhn/TrackingServiceInfoManage.do?serviceId=<%=bean.getServiceId()%>&readOnly=false">修改</a>
       <%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='TrackingServiceInfoManage.do?trackingId=<%=trackingId%>'">
  	<%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>


</body>
</html>