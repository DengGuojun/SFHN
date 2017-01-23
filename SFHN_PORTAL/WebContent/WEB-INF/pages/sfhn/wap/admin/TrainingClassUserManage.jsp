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
DeclareReportBean declareReportBean = (DeclareReportBean)request.getAttribute("DeclareReportBean");
Map<Integer, String> industryTypeMap = (Map<Integer, String>)request.getAttribute("IndustryTypeMap");
Integer fileId = (Integer)request.getAttribute("fileId");
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
	<title>学员详情</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<dl class="info-list mb18">
				<dd class="list-item"><a>学员信息采集<i class="icon-arrow" onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileId %>'"></i></a></dd>
			</dl>
			<dl class="info-list mb18">
				<dd class="list-item"><span>姓名：</span><span class="fr item-tips"><%=declareReportBean.getUserName()%></span></dd>
				<dd class="list-item"><span>性别：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(declareReportBean.getUserGender(), GenderConfig.GENDER_MAP) %></span></dd>
				<dd class="list-item"><span>文化程度：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(declareReportBean.getEducation(), FarmerInfoConfig.EDUCATION_LEVEL_MAP) %></span></dd>
				<dd class="list-item"><span>身份证号：</span><span class="fr item-tips"><%=declareReportBean.getIdentityNumber() %></span></dd>
				<dd class="list-item"><span>年龄：</span><span class="fr item-tips"></span></dd>
			</dl>
			<dl class="info-list mb18">
				<dd class="list-item"><span>家庭人口：</span><span class="fr item-tips"><%=declareReportBean.getFamilyPerson() >0 ?  declareReportBean.getFamilyPerson() : ""%></span></dd>
				<dd class="list-item"><span>类型：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(declareReportBean.getEconomicAreaType(), FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_MAP)%></span></dd>
				<dd class="list-item"><span>从事产业类型：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(declareReportBean.getIndustryTypeId1(),industryTypeMap) %></span></dd>
				<dd class="list-item"><span>规模：</span><span class="fr item-tips"><%=declareReportBean.getIndustryScale1() > 0 ? declareReportBean.getIndustryScale1() : "" %><%=declareReportBean.getIndustryUnit1()%></span></dd>
				<dd class="list-item"><span>产业收入：</span><span class="fr item-tips"><%=declareReportBean.getLastYearIncome() > 0 ? declareReportBean.getLastYearIncome() : ""%></span></dd>
				<dd class="list-item"><span>培育类型：</span><span class="fr item-tips"><%=MapKit.getValueFromMap(declareReportBean.getDeclareType(),DeclareInfoConfig.DECLARE_TYPE_MAP) %></span></dd>
			</dl>
			<dl class="info-list mb18">
				<dd class="list-item"><span>电话：</span><span class="fr item-tips"><%=declareReportBean.getUserMobile() %></span></dd>
				<dd class="list-item"><span>家庭住址：</span><span class="fr item-tips"><%=declareReportBean.getProvince()%><%=declareReportBean.getCity()%><%=declareReportBean.getRegion()%><%=declareReportBean.getAddress() %></span></dd>
			</dl>
	</div>
</body>
</html>