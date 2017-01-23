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
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%
	TrainingClassInfoBean bean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingOrganizationInfoBean trainingOrganizationInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	ProcessLogHelper processLogHelper = new ProcessLogHelper();
	List<ProcessLogBean> processLogList = (List<ProcessLogBean>) request.getAttribute("ProcessLogList");
	boolean isOwner = (Boolean) request.getAttribute("IsOwner");
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
	<title>学员招生流程</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>/css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="main-section">
				<div class="main-hd pd43">
					<h4><%=bean.getClassName() %> 学员招生流程</h4>
				</div>
				<div class="main-bd">
					<ul>
						<li><label class="u-title"><span>培训机构：</span></label>
						<div class="u-content"><span><%=trainingOrganizationInfoBean.getOrganizationName() %></span></div></li>
						<li><label class="u-title"><span>流程状态：</span></label>
						<div class="u-content"><span><%=MapKit.getValueFromMap(bean.getRecruitStatus(), TrainingClassInfoConfig.RECRUIT_STATUS_MAP) %></span></div></li>
						<li><label class="u-title"><span>当前操作人：</span></label>
							<div class="u-content">
							<span>
							<% String currentOperator = "";
							   if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_START)){
								   currentOperator = trainingOrganizationInfoBean.getOrganizationName();
							   }else if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_CONTINUE)){
								   currentOperator = bean.getRegion() + "主管部门";
							   }else if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH)){
								   currentOperator = "完成";
							   }
							 %>
							<%=currentOperator %>
							</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div class="mg-wrp text-right">			    
				<%if(isOwner) {%>
				<a class="btn btn-success">
					<button onclick="javascript:location.href='TrainingClassRecruitVerify.do?classId=<%=bean.getClassId() %>'">核实生源</button>
				</a>
				<%} else{%>
				<%if(!bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH)) {%>
				<form method="post"  enctype="multipart/form-data" action="TrainingClassRecruitManage.do?classId=<%=bean.getClassId() %>" id="recruitImport">
				<div class="mb18">
			   <a class="btn btn-success btn-upfile int-upfile"><input type="file" id="file" name="file" class="up-file" onchange="submitForm();" ><button>上传新名单</button></a>
			   </div>
		       </form>
				<%} %>
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
							   if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_START)){
								   nextOperator = trainingOrganizationInfoBean.getOrganizationName();
								   nextAction = "核实生源";
							   }else if( bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_CONTINUE)){
								   nextOperator = bean.getRegion() + "主管部门";
								   nextAction = "上传新名单";
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
<script type="text/javascript">
function submitForm() {
       document.getElementById("recruitImport").submit();
 }
</script>
</html>