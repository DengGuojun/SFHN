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
<%@ page import="com.lpmas.sfhn.console.config.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<MajorInfoBean> list = (List<MajorInfoBean>)request.getAttribute("MajorInfoList");
	
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	MajorTypeBean typeBean = (MajorTypeBean)request.getAttribute("MajorTypeBean");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单位信息管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">
	 <%=typeBean.getMajorName() %> - 专业信息列表
</p>
<form name="formSearch" method="post" action="MajorInfoList.do">
  <div class="search_form">
    <em class="em1">专业信息名称：</em>
    <input type="text" name="majorName" id="majorName" value="<%=ParamKit.getParameter(request, "majorName", "") %>" size="20"/>
    <input type="hidden" name="typeId" id="typeId" value="<%=typeBean.getMajorId() %>"/>
    <%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.SEARCH)){ %>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
    <%} %>
  </div>
  <table width="100%" border="0" cellpadding="0" class="table_style">
    <tr>
      <th>专业信息ID</th>
      <th>专业信息名称</th>
      <th>操作</th>
    </tr>
    <%
    for(MajorInfoBean bean:list){ 
    %>	
    <tr>
      <td><%=bean.getMajorId()%></td>
      <td><%=bean.getMajorName() %></td>
      <td align="center">
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %>
      	 <a href="/sfhn/MajorInfoManage.do?majorId=<%=bean.getMajorId() %>">修改</a>
      	 <%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
 		<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='MajorInfoManage.do?typeId=<%=typeBean.getMajorId() %>'">
 	<%} %>
  <input type="button" name="button" id="button" value="返回" onclick="javascript:location.href='MajorTypeList.do'">
</li>
<%@ include file="../include/page.jsp" %>
</ul>
</body>
</html>