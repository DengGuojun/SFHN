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
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingClassAcceptBean acceptBean = (TrainingClassAcceptBean)request.getAttribute("ClassAccept");
	TrainingOrganizationInfoBean trainingOrganizationInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	Map<Integer, Integer> fileInfoMap  = (Map<Integer, Integer>)request.getAttribute("FileInfoMap");
	int centralizedMaterialCount = (Integer)request.getAttribute("CentralizedMaterialCount");
	int fieldMaterialCount = (Integer)request.getAttribute("FieldMaterialCount");
	String lackOfMaterial = (String)request.getAttribute("LackOfMaterial");
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
	<title>验收项目详情</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
	
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="main-section">
				<div class="main-bd">
					<ul>
						<li><label class="u-title"><span>培训机构：</span></label><div class="u-content"><span><%=trainingOrganizationInfoBean.getOrganizationName() %></span></div></li>
						<li><label class="u-title"><span>培训班：</span></label><div class="u-content"><span><%=classInfoBean.getClassName() %></span></div></li>
						<li><label class="u-title"><span>办班地点：</span></label><div class="u-content"><span><%=acceptBean.getTrainingAddress() %></span></div></li>
						<li><label class="u-title"><span>验收重点内容：</span></label><div class="u-content"><span class="text-left" id="acceptContent"><%=acceptBean.getAcceptContent() %></span></div></li>
					</ul>
				</div>
			</div>
			<div class="main-section">
				<div class="main-hd pd43 little">
					<h4>已提交内容</h4>
				</div>
				<div class="main-bd detail-box js-detail">
					<ul>
						<li class="detail-wrp">
							<a href="FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM)%>">
							<i class="icon-detail"></i>
							<div class="detail-cont">
								<div class="cont-title">项目验收表</div>
							</div>
							</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="main-section">
				<div class="main-bd">
					<dl class="info-list mb18">
						<dd class="list-item" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>上传集中培训资料</span><span class="fr">已上传<b><%=centralizedMaterialCount %>/17</b><i> ></i></span></dd>
						<dd class="list-item" onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>上传田间实训资料</span><span class="fr">已上传<b><%=fieldMaterialCount %>/2</b><i> ></i></span></dd>
					</dl>
				</div>
			</div>
			<div class="main-section mb18">
				<div class="main-hd pd43 little">
					<h4>欠缺材料</h4>
				</div>
				<div class="main-bd pd43 white-bg">
					<div class="global-title"><%=lackOfMaterial %></div>
				</div>
			</div>
			<div class="main-section">
				<div class="global-tips">大文件可以在电脑端下载</div>
			</div>
		    <div class="btn-group">
				<div class="btn btn-large btn-reject js-reject"><button type="button" >驳回</button></div>
				<div class="btn btn-large btn-pass fr js-pass"><button type="button" >通过</button></div>
			</div>
		</div>
	</div>
	<!-- popup -->
	<div class="mask dn"></div>
	<div id="pass-reason" class="popup-wrp popup-theme dn white-bg">
		<form id="formData1" class="form-horizontal" method="post" action="TrainingClassAcceptApprove.do" onsubmit="javascript:return checkForm('formData1');">
		<textarea class="pd-text popup-text" name="reason" placeholder="主要问题及意见" checkStr="主要问题及意见;txt;true;;1000"></textarea>
		    <input type="hidden" name="action" id="action" value="<%=TrainingClassInfoConfig.APPROVE_ACTION_PASS%>"/>
		    <input type="hidden" name="classId" value="<%=acceptBean.getClassId() %>"/>			
		<div class="text-center mb40">
			<div class="btn btn-large btn-pass"><button>提交</button></div>
		</div>
		</form>
	</div>
	<div id="reject-reason" class="popup-wrp popup-theme dn white-bg">
		<form id="formData2" class="form-horizontal" method="post" action="TrainingClassAcceptApprove.do" onsubmit="javascript:return checkForm('formData2');">
		<textarea class="pd-text popup-text" name="reason" placeholder="主要问题及整改意见" checkStr="主要问题及整改意见;txt;true;;1000"></textarea>
		    <input type="hidden" name="action" id="action" value="<%=TrainingClassInfoConfig.APPROVE_ACTION_FAIL%>"/>
		    <input type="hidden" name="classId" value="<%=acceptBean.getClassId() %>"/>			
		<div class="text-center mb40">
			<div class="btn btn-large btn-pass"><button>提交</button></div>
		</div>
		</form>
	</div>
	<!-- popup -->
</body>
<script>
$(document).ready(function() {
	$('#acceptContent').html($('#acceptContent').html().replace(/\n/g,'<br/>'));
});
$(function(){
	$('.js-pass').click(function(){
		if($('.js-detail li.selected').length != $('.js-detail li').length){
			alert("请先查阅附件");
			return;
		}
		$('.mask').removeClass('dn');
		$('#pass-reason').removeClass('dn');
	});
	
	$('.js-reject').click(function(){
		if($('.js-detail li.selected').length != $('.js-detail li').length){
			alert("请先查阅附件");
			return;
		}
		$('.mask').removeClass('dn');
		$('#reject-reason').removeClass('dn');
	});
	
	$('.js-close,.mask').click(function(){
		$('.mask').addClass('dn');
		$('.popup-wrp').addClass('dn');
	});
	
	$('.js-detail li').click(function(){
		if($(this).hasClass('selected')){
		}else{
			$(this).addClass('selected');
		}
	});
})

</script>
</html>