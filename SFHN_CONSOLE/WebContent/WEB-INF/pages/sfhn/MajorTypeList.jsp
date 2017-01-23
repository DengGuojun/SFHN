<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ page import="com.lpmas.sfhn.console.config.*"%>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<MajorTypeBean> list = (List<MajorTypeBean>)request.getAttribute("MajorTypeList");

	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业类型管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">
	 专业类型列表 
</p>
<form name="formSearch" method="post" action="MajorTypeList.do">
  <div class="search_form">
    <em class="em1">专业类型名称：</em>
    <input type="text" name="majorName" id="majorName" value="<%=ParamKit.getParameter(request, "majorName", "") %>" size="20"/>
     <%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)){ %>
     <input name="" type="submit" class="search_btn_sub" value="查询"/>
     <%} %>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>专业类型ID</th>
      <th>专业类型名称</th>
      <th>操作</th>
    </tr>
    <%
    for(MajorTypeBean bean:list){ 
    %>	
    <tr>
      <td><%=bean.getMajorId()%></td>
      <td><%=bean.getMajorName() %></td>
      <td align="center">
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %>
      	 <a href="/sfhn/MajorTypeManage.do?majorId=<%=bean.getMajorId() %>">修改</a> |
      	<%} %> 
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)){ %>
      	 <a href="/sfhn/MajorInfoList.do?typeId=<%=bean.getMajorId() %>">查看专业信息</a>
      	<%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
  		<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='MajorTypeManage.do'">
  	<%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>