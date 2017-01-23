<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
	TeacherInfoBean bean = (TeacherInfoBean)request.getAttribute("TeacherInfoBean");
%>
<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>师资详情</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<dl class="info-list mb18">
				<dd class="list-item"><span>姓名：</span><span class="fr item-tips"><%=bean.getTeacherName()%></span></dd>
				<dd class="list-item"><span>性别：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(bean.getTeacherGender(), GenderConfig.GENDER_MAP) %></span></dd>
			</dl>
			<dl class="info-list mb18">		
				<dd class="list-item"><span>类型：</span><span class="fr item-tips"><%=bean.getTearcherType() %></span></dd>
				<dd class="list-item"><span>专业：</span><span class="fr item-tips"><%=bean.getTeacherMajor() %></span></dd>
				<!-- <dd class="list-item"><span>主讲课程：</span><span class="fr item-tips"><%=bean.getMainCourse()%></span></dd> -->
			</dl>
			<dl class="info-list mb18">
				<dd class="list-item"><span>手机：</span><span class="fr item-tips"><%=bean.getTeacherMobile() %></span></dd>
				<dd class="list-item"><span>地区：</span><span class="fr item-tips"><%=bean.getProvince() %></span></dd>
				<dd class="list-item"><span>工作单位：</span><span class="fr item-tips"><%=bean.getCorporateName()%></span></dd>
				<!-- <dd class="list-item"><span>家庭住址：</span><span class="fr item-tips"><%=bean.getAddress() %></span></dd> -->
			</dl>
	</div>
</body>
</html>