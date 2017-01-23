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
    ExpertInfoBean bean = (ExpertInfoBean)request.getAttribute("ExpertInfoBean");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
%>
<%@ include file="../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>专家管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
    <div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="TrainingOrganizationInfoList.do">专家信息列表</a>&nbsp;>&nbsp;</li>
			<% if(bean.getExpertId() > 0) {%>
			<li><%=bean.getExpertName() %>&nbsp;>&nbsp;</li>
			<li>修改专家信息</li>
			<%}else{ %>
			<li>新建专家信息</li>
			<%}%>
		</ul>
	</div>
    <form id="formData" name="formData" method="post" action="ExpertInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="expertId" id="expertId" value="<%=bean.getExpertId() %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>专家名称：</em>
	      <input type="text" name="expertName" id="expertName" size="20" maxlength="50" value="<%=bean.getExpertName() %>" checkStr="专家名称;txt;true;;50"/>
	    </p>	 
	    <p>
	      <em class="int_label"><span>*</span>专家类型：</em>
	      <input type="text" name="expertType" id="expertType" size="20" maxlength="50" value="<%=bean.getExpertType() %>" checkStr="专家类型;txt;true;;50"/>
	    </p>	 
	    <p>
	      <em class="int_label"><span>*</span>专家专业：</em>
	      <input type="text" name="expertMajor" id="expertMajor" size="20" maxlength="50" value="<%=bean.getExpertMajor() %>" checkStr="专家专业;txt;true;;50"/>
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