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
List<String> list = (List<String>)request.getAttribute("message");
List<String> messageDeleteList = (List<String>)request.getAttribute("messageDelete");
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
	<title>上传结果</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
	<div class="container-bd mt0">
			<section class="r-info mb18 pd43">
			     <%if(!list.isEmpty() && list.size() > 0){ %>
				<div class="r-info-hd"><%=list.get(0) %></div>
				<%} %>
				<div class="d-table r-info-box">
				<%for(int i = 1; i < list.size(); i++) { %>
				<div class="d-row">
				<%String[] sourceStrArray = list.get(i).split(",", 3); %>
		          <div class="d-cell"><%=sourceStrArray[0]%></div>
				  <div class="d-cell"><%=sourceStrArray[1]%></div>
				  <div class="d-cell r-info-account"><%=sourceStrArray[2]%></div>
				 </div>
		         <%} %>
		         </div>
			</section>
			<%if(messageDeleteList != null &&!messageDeleteList.isEmpty()){ %>
			<section class="r-info mb18 pd43">
				<div class="d-table r-info-box">
				<%for(int i = 0; i < messageDeleteList.size(); i++) { %>
				<div class="d-row">
				<%String[] sourceStrArray = list.get(i).split(",", 3); %>
		          <div class="d-cell"><%=sourceStrArray[0]%></div>
				  <div class="d-cell"><%=sourceStrArray[1]%></div>
				  <div class="d-cell r-info-account"><%=sourceStrArray[2]%></div>
		         <%} %>
		          </div>
		         </div>
			</section>
			<%} %>
   </div>
   </div>
</body>
</html>