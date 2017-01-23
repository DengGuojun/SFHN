<%@page import="com.lpmas.sfhn.portal.config.TrainingClassUserConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.constant.user.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	FileInfoBean fieldTrainingPlan = (FileInfoBean)request.getAttribute("FieldTrainingPlan");
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
	boolean isOwner = (Boolean)request.getAttribute("IsOwner");
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
	<title>湘农科教云管理后台</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="g-nav mb18">
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>'"><span>学员名单</span></div>
				<div class="nav-item w-5-1 fl selected"><span>年度计划</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>集中培训</span></div>
				<div class="nav-item w-5-1 fl"  onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>田间实训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='ExpertInfoList.do?classId=<%=classInfoBean.getClassId()%>'"><span>跟踪服务</span></div>
			</div>
			<input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">项目年度实施方案</span>
							<span class="fr cont-tips">
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN).getFileId()%>'">下载</button></div>
								<%}else{%>
									未上传
								<% } %>
							</span>	
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">培训指南</span>
							<span class="fr cont-tips">
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE).getFileId()%>'">下载</button></div>
								<%}else{%>
									未上传
								<% } %>
							</span>	
					</div>
				</div>
			</section>
		</div>
	</div>
</body>
</html>