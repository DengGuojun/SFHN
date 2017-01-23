<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<% 
	TrackingServiceInfoBean bean = (TrackingServiceInfoBean)request.getAttribute("TrackingServiceInfoBean");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>跟踪信息管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
    <div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="TrackingServiceInfoList.do?trackingId=<%=bean.getTrackingId()%>">跟踪服务信息列表</a>&nbsp;>&nbsp;</li>
			<% if(bean.getServiceId() > 0) {%>
			<li>修改跟踪服务信息</li>
			<%}else{ %>
			<li>新建跟踪服务信息</li>
			<%}%>
		</ul>
	</div>
    <form id="formData" name="formData" method="post" action="TrackingServiceInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="trackingId" id="trackingId" value="<%=bean.getTrackingId() %>"/>
	  <input type="hidden" name="serviceId" id="serviceId" value="<%=bean.getServiceId() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>跟踪服务标题：</em>
	      <input type="text" name="trackingTitle" id="trackingTitle" size="20" maxlength="50" value="<%=bean.getTrackingTitle() %>" checkStr="跟踪服务信息标题;txt;true;;50"/>
	    </p>	
	    <p>
	      <em class="int_label"><span>*</span>跟踪服务类型：</em>
	      <input type="text" name="serviceType" id="serviceType" size="20" maxlength="50" value="<%=bean.getServiceType() %>" checkStr="跟踪服务类型;txt;true;;50"/>
	    </p>
	    <p>
	      <em class="int_label"><span>*</span>跟踪服务内容：</em>
	      <input type="text" name="trackingContent" id="trackingContent" size="20" maxlength="50" value="<%=bean.getTrackingContent() %>" checkStr="跟踪服务信息内容;txt;true;;50"/>
	    </p>	 
	    <p>
	      <em class="int_label"><span>*</span>跟踪服务反馈：</em>
	      <input type="text" name="trackingFeedback" id="trackingFeedback" size="20" maxlength="50" value="<%=bean.getTrackingFeedback() %>" checkStr="跟踪服务信息反馈;txt;true;;50"/>
	    </p>
	    <p>
	      <em class="int_label">有效状态：</em>
	      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
	    </p>
	  </div>
	  <div class="div_center">
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  	<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
});
</script>
</html>