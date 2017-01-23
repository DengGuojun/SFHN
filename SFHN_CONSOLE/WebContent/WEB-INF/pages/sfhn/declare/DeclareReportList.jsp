<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ page import="com.lpmas.admin.bean.*"%>
<%@ page import="com.lpmas.admin.business.*"%>
<%@ page import="com.lpmas.admin.config.*"%>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<DeclareReportBean> list = (List<DeclareReportBean>)request.getAttribute("DeclareReportList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>
<%@ include file="../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>职业农民</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">职业农民申请信息列表</p>
<form name="formSearch" method="post" action="DeclareReportList.do">
<div class="search_form">
  	<em class="em1">姓名：</em>
    <input type="text" name="userName" id="userName" value="<%=ParamKit.getParameter(request, "userName", "") %>" size="20"/>
    
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>申报ID</th>
      <th>姓名</th>
      <th>申报类型</th>
      <th>申报状态</th>
      <th>申报时间</th>
      <th>操作</th>
    </tr>
    <%
    for(DeclareReportBean bean:list){%> 
    <tr>
      <td><%=bean.getDeclareId() %></td>
      <td><%=bean.getUserName() %></td>
      <td><%=DeclareInfoConfig.DECLARE_TYPE_MAP.get(bean.getDeclareType())%></td>
      <td><%=DeclareInfoConfig.DECLARE_STATUS_MAP.get(bean.getDeclareStatus())%></td>
      <td><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>
      <td align="center">
      	<a href="/sfhn/DeclareReportManage.do?declareId=<%=bean.getDeclareId()%>">查看</a> 
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
  	<input type="button" name="button" id="button" value="导出到Excel" onclick="javascript:location.href='DeclareReportExport.do'">
  	<input type="button" name="button" id="button" value="同步到mongo" onclick="javascript:location.href='DeclareReportSync.do'">
</li>
<%@ include file="../../include/page.jsp" %>
</ul>
</body>
</html>