<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
String identityNumber = (String)request.getAttribute("identityNumber");
String userName = (String)request.getAttribute("userName");
String userGender = (String)request.getAttribute("userGender");
String education = (String)request.getAttribute("education");
String industryScale = (String)request.getAttribute("industryScale");
String industryType = (String)request.getAttribute("industryType");
String message = (String)request.getAttribute("message");
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
	<title>招生筛选</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd">
			<div class="main-section">
				<div class="main-bd pd43 mb18 white-bg">
				<%if(!userName.isEmpty()){ %>
					<div class="mn-info">
						<p class="info-name"><%=userName %></p>
						<p class="info-particular"><%=identityNumber %></p>
						<p class="info-particular">
							<span><%=userGender %></span>&emsp;&emsp;
							<span><%=education %></span>&emsp;&emsp;
							<span><%=industryType %></span>&emsp;&emsp;
							<span><%=industryScale %></span>
						</p>
					</div>
				<%}else{%>
				<div class="mn-info">
						<p class="info-particular"><%=identityNumber %></p>						
					</div>
				<%} %>
					<p class="mn-info-tips"><%=message %></p>
				</div>
			</div>
			<div class="pd43 pt0">
				<div class="btn btn-submit w-1-1"><button onclick="javascript:history.go(-1);">返回</button></div>
			</div>
		</div>
	</div>
</body>
</html>