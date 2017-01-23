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
	TrainingClassTargetBean bean = (TrainingClassTargetBean)request.getAttribute("TrainingClassTargetBean");
	List<TrainingOrganizationInfoBean> trainingOrgList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrgList");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int organizationId = bean.getOrganizationId();
	String readOnly = ParamKit.getParameter(request, "readOnly","false").trim();
	request.setAttribute("readOnly", readOnly);
	int userId = (int)request.getAttribute("userId");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
%>
<%@ include file="../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>培训班指标管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
    <div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="TrainingClassTargetList.do">培训指标列表</a>&nbsp;>&nbsp;</li>
			<% if(organizationId > 0) {%>
			<li>修改培训机构指标</li>
			<%}else{ %>
			<li>新建培训机构指标</li>
			<%}%>
		</ul>
	</div>
    <form id="formData" name="formData" method="post" action="TrainingClassTargetManage.do" onsubmit="javascript:return checkForm('formData');">
	  <input type="hidden" name="targetId" id="targetId" value="<%=bean.getTargetId() %>"/>
	  <div class="modify_form">
	  	<p>
	      <em class="int_label"><span>*</span>培育类型：</em>
	      <select class="form-select" name="trainingType" checkStr="培育类型;txt;true;;200" >
	      		<option value="">请选择</option>
                    <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
						<option value="<%=statusBean.getStatus()%>"  <%=(bean.getTrainingType() == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
					<%} %>
           </select>
	    </p>  
	    <p>
	      <em class="int_label"><span>*</span>培育年份：</em>
	      <select id="trainingYear" name="trainingYear"  checkStr="培育年份;txt;true;;100">
			<option value="">请选择</option>
			<%
				String trainingYear = bean.getTrainingYear();
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
     	  <select id="province" name="province"  onchange="$('#region').empty();getCityNameList('province','city','')" checkStr="省;txt;false;;200"></select>
     	  <input type="hidden" id="provinceDummy" value="<%=bean.getProvince()%>"/>
	    </p> 
	    <p>
	      <em class="int_label">市:</em>    
     	  <select id="city" name="city" onchange="getRegionNameList('city','region','')" checkStr="市;txt;false;;200"></select>
     	  <input type="hidden" id="cityDummy" value="<%=bean.getCity()%>"/>
	    </p>
	    <p>
	      <em class="int_label">区:</em>    
     	  <select id="region" name="region"  checkStr="区;txt;false;;200"></select>
	    </p>
	    <p>
	      <em class="int_label">培训机构：</em>
	      <select id="organizationId" name="organizationId"  checkStr="培训机构;txt;false;;100">
			<option value="">请选择</option>
			<%
				for (TrainingOrganizationInfoBean trainingOrgBean : trainingOrgList) {
			%>
			<option value="<%=trainingOrgBean.getOrganizationId()%>" <%=bean.getOrganizationId() == trainingOrgBean.getOrganizationId() ? "selected" : ""%>><%=trainingOrgBean.getOrganizationName()%></option>
			<%
				}
			%>
		</select> 
	    </p>  
	    <p>
	    	<em class="int_label"><span>*</span>培育指标：</em>
	      <input type="text" name="targetQuantity" id="targetQuantity" size="10" maxlength="10" value="<%=bean.getTargetQuantity() %>" checkStr="培育指标;num;true;;10"/>
	    </p>
	    <p><em>说明：若选择了省市区任意一项，则此指标代表政府机构指标，培训机构字段无效。</em></p>
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
		getProvinceNameList('country','province','<%=bean.getProvince()%>');
		getCityNameList('provinceDummy','city','<%=bean.getCity()%>');
		getRegionNameList('cityDummy','region','<%=bean.getRegion()%>');
	});
	var readOnly = '${readOnly}';
	if(readOnly=='true') {
		disablePageElement();
	}
});
</script>
</html>