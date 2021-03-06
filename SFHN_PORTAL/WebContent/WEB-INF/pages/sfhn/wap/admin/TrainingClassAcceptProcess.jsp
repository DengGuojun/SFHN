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
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingClassAcceptBean acceptBean = (TrainingClassAcceptBean)request.getAttribute("ClassAccept");
	TrainingOrganizationInfoBean trainingOrganizationInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	ProcessLogHelper processLogHelper = new ProcessLogHelper();
	List<ProcessLogBean> processLogList = (List<ProcessLogBean>) request.getAttribute("ProcessLogList");
	String logContent = (String) request.getAttribute("LogContent");
	boolean HasApprovePermission = (Boolean) request.getAttribute("HasApprovePermission");
	boolean isOwner = (Boolean) request.getAttribute("IsOwner");
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
	<title>项目验收流程</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>/css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="main-section">
				<div class="main-hd pd43">
					<h4><%=classInfoBean.getClassName() %> 项目验收流程</h4>
				</div>
				<div class="main-bd">
					<ul>
						<li><label class="u-title"><span>培训机构：</span></label>
						<div class="u-content"><span><%=trainingOrganizationInfoBean.getOrganizationName() %></span></div></li>
						<li><label class="u-title"><span>流程状态：</span></label>
						<div class="u-content"><span><%=MapKit.getValueFromMap(acceptBean.getAcceptStatus(), TrainingClassInfoConfig.ACCEPT_STATUS_MAP) %></span></div></li>
						<li><label class="u-title"><span>当前操作人：</span></label>
							<div class="u-content">
							<span>
							<% String currentOperator = "";
							   if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT) 
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION)
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY)
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE)){
								   currentOperator = trainingOrganizationInfoBean.getOrganizationName();
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)){
								   currentOperator = classInfoBean.getRegion() + "主管部门";
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)){
								   currentOperator = classInfoBean.getCity() + "主管部门";
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)){
								   currentOperator = classInfoBean.getProvince() + "主管部门";
							   }else{
								   currentOperator = "完成";
							   }
							%>
							<%=currentOperator %>
							</span>
							</div>
						</li>
						<%if(StringKit.isValid(logContent)){%>	
						<li><label class="u-title"><span>操作意见：</span></label>
						<div class="u-content"><span><%=logContent%></span></div></li>
						<%}%>
					</ul>
				</div>
			</div>
			<div class="mg-wrp text-right">
				<%if(HasApprovePermission) {%>
				<a class="btn btn-success">
					<button onclick="javascript:location.href='TrainingClassAcceptApprove.do?classId=<%=acceptBean.getClassId() %>'">审批</button>
				</a>
				<%} %>
				<%if(isOwner && (acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE)) ) {%>
				<a class="btn btn-success">
					<button onclick="javascript:location.href='TrainingClassAcceptManage.do?classId=<%=acceptBean.getClassId() %>'">修改</button>
				</a>
				<%} %>
				<%if(isOwner && (acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY)
						|| acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE)) ) {%>
				<a class="btn btn-success">
					<button onclick="javascript:location.href='TrainingClassAcceptCommit.do?classId=<%=acceptBean.getClassId() %>&action=<%=TrainingClassInfoConfig.COMMIT_ACTION_COMMIT%>'">重新提交</button>
				</a>
				<%} %>
				<%if(isOwner && acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)){%>
				<a class="btn btn-success">
					<button onclick="javascript:location.href='TrainingClassAcceptCommit.do?classId=<%=acceptBean.getClassId() %>&action=<%=TrainingClassInfoConfig.COMMIT_ACTION_CANCEL_COMMIT%>'">撤回</button>
				</a>
				<%} %>
			</div>
			<div class="main-section">
				<div class="main-hd pd43 little">
					<h4>流转过程</h4>
				</div>
				<div class="main-bd cont-wrp">
					<ul>
						<%for(ProcessLogBean processLogBean: processLogList) {%>
						<li>
							<span class="mbd-hd"><%=DateKit.formatTimestamp(processLogBean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT) %></span>
							<strong class="mbd-bd"><%=processLogHelper.getOperatorOrganizationName(processLogBean)%></strong>
							<span class="mbd-ft"><%=processLogHelper.getProcessTypeDescription(processLogBean)%></span>
						</li>
						<%} %>
						<!-- 当前流程 -->
						<li>
							<% String nextOperator = "";
							   String nextAction = "";
							   if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_EDIT) 
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_REGION)
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_CITY)
									   || acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_REJECTED_PROVINCE)){
								   nextOperator = trainingOrganizationInfoBean.getOrganizationName();
								   nextAction = "待修改";
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_REGION)){
								   nextOperator = classInfoBean.getRegion() + "主管部门";
								   nextAction = "待县主管部门审批";
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_CITY)){
								   nextOperator = classInfoBean.getCity() + "主管部门";
								   nextAction = "待市主管部门审批";
							   }else if(acceptBean.getAcceptStatus().equals(TrainingClassInfoConfig.ACCEPT_STATUS_WAIT_APPROVE_PROVINCE)){
								   nextOperator = classInfoBean.getProvince() + "主管部门";
								   nextAction = "待省主管部门审批";
							   }
							%>
							<strong class="mbd-bd">
							<%=nextOperator%>
							</strong>
							<span class="mbd-ft font-y"><%=nextAction %></span>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>