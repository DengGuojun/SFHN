<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<% 
	IndustryBeltInfoBean bean = (IndustryBeltInfoBean)request.getAttribute("IndustryBeltInfoBean");
	AdminUserHelper adminHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int beltId = bean.getBeltId();
%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产业带管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="MajorTypeList.do">产业带管理</a>&nbsp;>&nbsp;</li>
			<% if(beltId > 0) {%>
			<li><%=bean.getBeltName()%>&nbsp;>&nbsp;</li>
			<li>修改产业带</li>
			<%} else { %>
			<li>新建产业带</li>
			<%} %>
		</ul>
	</div>
	<form id="formData" name="formData" method="post" action="IndustryBeltInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="beltId" id="beltId" value="<%=beltId %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>产业带名称：</em>
	      <input type="text" name="beltName" id="beltName" size="30" maxlength="100" value="<%=bean.getBeltName() %>" checkStr="产业带名称;txt;true;;100"/>
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