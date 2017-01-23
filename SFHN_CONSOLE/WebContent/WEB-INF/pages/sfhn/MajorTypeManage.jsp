<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<% 
	MajorTypeBean bean = (MajorTypeBean)request.getAttribute("MajorTypeBean");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int majorId = bean.getMajorId();
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>专业类型管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="MajorTypeList.do">专业类型管理</a>&nbsp;>&nbsp;</li>
			<% if(majorId > 0) {%>
			<li><%=bean.getMajorName()%>&nbsp;>&nbsp;</li>
			<li>修改专业类型</li>
			<%} else { %>
			<li>新建专业类型</li>
			<%} %>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="MajorTypeManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="majorId" id="majorId" value="<%=majorId %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>专业类型名称：</em>
	      <input type="text" name="majorName" id="majorName" size="30" maxlength="100" value="<%=bean.getMajorName() %>" checkStr="专业类型名称;txt;true;;100"/>
	    </p>
	   <input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>"/>
	  </div>
	  <div class="div_center">
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  	<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
</html>