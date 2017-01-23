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
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<% 
	Integer activeCodeCount = (Integer)request.getAttribute("activeCodeCount");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	
	String province = ParamKit.getParameter(request, "province", "").trim();
	String city = ParamKit.getParameter(request, "city", "").trim();
	String region = ParamKit.getParameter(request, "region", "").trim();
	String trainingYear = ParamKit.getParameter(request, "trainingYear", "").trim();
%>
<%@ include file="../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>激活码管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
    <div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="ActiveCodeInfoList.do">激活码列表</a>&nbsp;>&nbsp;</li>
		</ul>
	</div>
    <form id="formData" name="formData" method="post" action="ActiveCodeInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <div class="modify_form"> 
	    <p>
	      <em class="int_label"><span>*</span>培育年份：</em>
	      <select id="trainingYear" name="trainingYear"  checkStr="培育年份;txt;true;;100">
			<option value="">请选择</option>
			<%
				for (Integer infoYear : trainingOrganizationInfoHelper.getTrainingYearList()) {
			%>
			<option value="<%=infoYear%>" <%=trainingYear.equals(String.valueOf(infoYear)) ? "selected" : ""%>><%=infoYear%></option>
			<%
				}
			%>
		</select> 
	    </p>  
	    <input type="hidden" name="country" id="country" value="中国">
	    <p>
	      <em class="int_label">省:</em>    
     	  <select id="province" name="province"  onchange="$('#region').empty();getCityNameList('province','city','')" checkStr="省;txt;true;;200"></select>
     	  <input type="hidden" id="provinceDummy" value="<%=province%>"/>
	    </p> 
	    <p>
	      <em class="int_label">市:</em>    
     	  <select id="city" name="city" onchange="getRegionNameList('city','region','')" checkStr="市;txt;false;;200"></select>
     	  <input type="hidden" id="cityDummy" value="<%=city%>"/>
	    </p>
	    <p>
	      <em class="int_label">区:</em>    
     	  <select id="region" name="region"  checkStr="区;txt;false;;200"></select>
	    </p> 
	    <p>
	      <em class="int_label">新增激活码数量:</em>    
     	  <input type="text" id="count" name="count"  checkStr="数量;num;true;;10"></select>
	    </p> 
	  </div>
	  <div class="div_center">
	  	<input type="hidden" name="status" id="status" value="<%=Constants.STATUS_VALID %>" />
	  	<input type="submit" name="submit" id="submit" class="modifysubbtn" value="提交" />
	  	<input type="button" name="cancel" id="cancel" value="取消" onclick="javascript:history.back()">
	  </div>
	</form>
</body>
<script>
$(document).ready(function() {
	var url='<%=REGION_URL%>/region/RegionAjaxList.do';
	$.getScript(url,function(data){
		getProvinceNameList('country','province','<%=province%>');
		getCityNameList('provinceDummy','city','<%=city%>');
		getRegionNameList('cityDummy','region','<%=region%>');
	});
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
});
</script>
</html>