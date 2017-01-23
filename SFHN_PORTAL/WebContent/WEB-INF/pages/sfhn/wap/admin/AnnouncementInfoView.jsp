<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%
	AnnouncementInfoBean bean = (AnnouncementInfoBean)request.getAttribute("AnnouncementInfo");
	FileInfoBean announcementAttach  = (FileInfoBean)request.getAttribute("AnnouncmentAttatch");
	boolean isOwner = (Boolean)request.getAttribute("isOwner");
%>

<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>公告详情</title>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
</head>
<body class="gray-bg pg-bottom">
	<div class="container">
		<div class="container-bd mt0">
			<input type="hidden" id="announcementId" value="<%=bean.getAnnouncementId() %>"/>
			<div class="notice-cont-info mb18">
				<%if(bean.getStatus() == Constants.STATUS_VALID) {%>
				<h3 class="notice-hd"><%=bean.getAnnouncementTitle() %></h3>
				<div class="notice-date"><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT) %></div>
				<p id="announcementContent">
					<%=bean.getAnnouncementContent() %>
				</p>
				<%} else{%>
				该公告已删除
				<%} %>
			</div>
			<%if(announcementAttach != null) {%>
				<a class="btn w-1-1 btn-upfile int-upfile"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=announcementAttach.getFileId()%>'"><%=announcementAttach.getFileName() %>.<%=announcementAttach.getFileFormat() %></button></a>
			<%} %>
			<%if(isOwner) {%>
			<div class="btn-group pt56">
				<div class="btn btn-large btn-remove  js-reject"><button id="delete" >删除</button></div>
				<div class="btn btn-large btn-edit fr  js-pass"><button onclick="javascript:location.href='AnnouncementInfoManage.do?announcementId=<%=bean.getAnnouncementId()%>'">编辑</button></div>
			</div>
			<%} %>
		</div>
	</div>
	
</body>
<script>
$(document).ready(function() {
	$('#announcementContent').html($('#announcementContent').html().replace(/\n/g,'<br/>'));
	$("#delete").click(function() {
		if(confirm("确定要删除吗?")){
			var id = $("#announcementId").val();
			var url = "AnnouncementInfoRemove.do?announcementId="+ id ;
			window.location.href= url
		 }
	});
});
</script>
</html>