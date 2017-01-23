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
	int centralizedMaterialCount = (Integer)request.getAttribute("CentralizedMaterialCount");
	int fieldMaterialCount = (Integer)request.getAttribute("FieldMaterialCount");
	String lackOfMaterial = (String)request.getAttribute("LackOfMaterial");
	int classId = (Integer)request.getAttribute("ClassId");
%>
<div class="main-section">
	<div class="main-hd pd43 little">
		<h4>请确认已提交内容</h4>
	</div>
	<div class="main-bd">
		<dl class="info-list mb18">
			<dd class="list-item" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classId%>'"><span>上传集中培训资料</span><span class="fr">已上传<b><%=centralizedMaterialCount %>/17</b><i> ></i></span></dd>
			<dd class="list-item" onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classId%>'"><span>上传田间实训资料</span><span class="fr">已上传<b><%=fieldMaterialCount %>/2</b><i> ></i></span></dd>
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