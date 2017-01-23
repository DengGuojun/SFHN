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
<% 
    GovernmentOrganizationInfoBean bean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrganizationInfoBean");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int organizationId = bean.getOrganizationId();
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
	String userId = (String)request.getAttribute("userId");
%>
<%@ include file="../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主管机构管理</title>
    <link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
    <div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="GovernmentOrganizationInfoList.do">主管机构列表</a>&nbsp;>&nbsp;</li>
			<% if(organizationId > 0) {%>
			<li><%=bean.getOrganizationName() %>&nbsp;>&nbsp;</li>
			<li>修改主管机构信息</li>
			<%}else{ %>
			<li>新建主管机构信息</li>
			<%}%>
		</ul>
	</div>
    <form id="formData" name="formData" method="post" action="GovernmentOrganizationInfoManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="organizationId" id="organizationId" value="<%=organizationId %>"/>
	  <div class="modify_form">
	    <p>
	      <em class="int_label"><span>*</span>主管机构名称：</em>
	      <input type="text" name="organizationName" id="organizationName" size="20" maxlength="50" value="<%=bean.getOrganizationName() %>" checkStr="主管机构名称;txt;true;;50"/>
	    </p>
	    <p>
      		<em class="int_label"><span>*</span>机构级别：</em>
				<select id="organizationLevel" name="organizationLevel" onchange="checkAddress()" checkStr="机构级别;txt;true;;100">
						<option value="">请选择</option>
						<%
							int level = bean.getOrganizationLevel();
							for (StatusBean<Integer, String> infoLevel : GovernmentOrganizationConfig.ORGANIZATION_LEVEL_LIST) {
						%>
						<option value="<%=infoLevel.getStatus()%>" <%=level == infoLevel.getStatus() ? "selected" : ""%>><%=infoLevel.getValue()%></option>
						<%
							}
						%>
				</select> 
		</p>  
	     <input type="hidden" name="country" id="country" value="中国">
	    <p id="provinceInput">
	      <em class="int_label"><span>*</span>省:</em>    
     	  <select id="province" name="province"  onchange="$('#region').empty();getCityNameList('province','city','')" checkStr="省;txt;false;;200"></select>
     	  <input type="hidden" id="provinceDummy" value="<%=bean.getProvince()%>"/>
	    </p> 
	    <p id="cityInput">
	      <em class="int_label"><span>*</span>市:</em>    
     	  <select id="city" name="city" onchange="getRegionNameList('city','region','')" checkStr="市;txt;false;;200"></select>
     	  <input type="hidden" id="cityDummy" value="<%=bean.getCity()%>"/>
	    </p>
	    <p id="regionInput">
	      <em class="int_label"><span>*</span>区:</em>    
     	  <select id="region" name="region"  checkStr="区;txt;false;;200"></select>
	    </p>
	    <p>
	      <em class="int_label">是否需要审批：</em>
	       <input type="checkbox" name="isNeedAudit" id="isNeedAudit" value="<%=Constants.SELECT_TRUE %>" <%=(bean.getIsNeedAudit() == Constants.SELECT_TRUE)?"checked":"" %>/>
	    </p>	    
	    <p>
	      <em class="int_label">有效状态：</em>
	      <input type="checkbox" name="status" id="status" value="<%=Constants.STATUS_VALID %>" <%=(bean.getStatus()==Constants.STATUS_VALID)?"checked":"" %>/>
	    </p>
	    <p class="p_top_border">
	      <em class="int_label">备注：</em>
	      <textarea name="memo" id="memo" cols="60" rows="3" checkStr="备注;txt;false;;1000"><%=bean.getMemo() %></textarea>
	    </p>	
	     <p>
	      <em class="int_label"><span>*</span>客户ID：</em>
	      <input type="text" name="userId" id="userId" size="20" maxlength="50" value="<%=userId %>" checkStr="客户ID;txt;true;;50"/>
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
	var url='<%=REGION_URL%>/region/RegionAjaxList.do';
	$.getScript(url,function(data){
		getProvinceNameList('country','province','<%=bean.getProvince()%>');
		getCityNameList('provinceDummy','city','<%=bean.getCity()%>');
		getRegionNameList('cityDummy','region','<%=bean.getRegion()%>');
	});
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
	checkAddress();
});
function checkAddress(){
	var level = $("#organizationLevel").val();
	if(level == "3"){
		$("#provinceInput").show();
		$("#cityInput").show();
		$("#regionInput").show();
	}else{
		$("#provinceInput").hide();
		$("#cityInput").hide();
		$("#regionInput").hide();
		if(level == "2"){
			$("#provinceInput").show();
			$("#cityInput").show();
		}else{
			$("#provinceInput").hide();
			$("#cityInput").hide();
			if(level == "1"){
				$("#provinceInput").show();
			}else{
				$("#provinceInput").hide();
			}
		}
	}
}
</script>
</html>