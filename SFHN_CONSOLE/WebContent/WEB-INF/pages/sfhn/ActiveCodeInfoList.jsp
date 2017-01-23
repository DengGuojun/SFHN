<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.admin.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.console.config.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<ActiveCodeInfoBean> list = (List<ActiveCodeInfoBean>)request.getAttribute("ActiveCodeInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
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
<title>激活码列表</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">激活码列表</p>
<form name="formSearch" method="post" action="ActiveCodeInfoList.do">
<div class="search_form">
	<p>
	      <em class="em1">培育年份：</em>
	      <select id="trainingYear" name="trainingYear"  checkStr="培育年份;txt;true;;100">
			<option value=""></option>
			<%for (Integer infoYear : trainingOrganizationInfoHelper.getTrainingYearList()) {%>
			<option value="<%=infoYear%>" <%=trainingYear.equals(String.valueOf(infoYear)) ? "selected" : ""%>><%=infoYear%></option>
			<%}%>
		</select> 
	    </p>  
	    <input type="hidden" name="country" id="country" value="中国">
	    <p>
	      <em class="em1">省:</em>    
     	  <select id="province" name="province"  onchange="$('#region').empty();getCityNameList('province','city','')"></select>
     	  <input type="hidden" id="provinceDummy" value="<%=province%>"/>
	    </p> 
	    <p>
	      <em class="em1">市:</em>    
     	  <select id="city" name="city" onchange="getRegionNameList('city','region','')"></select>
     	  <input type="hidden" id="cityDummy" value="<%=city%>"/>
	    </p>
	    <p>
	      <em class="em1">区:</em>    
     	  <select id="region" name="region"></select>
	    </p> 
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>激活码</th>
      <th>培育年份</th>
      <th>激活码状态</th>
      <th>省份</th>  
      <th>城市</th>   
      <th>县区</th>
    </tr>
    <%
    for(ActiveCodeInfoBean bean:list){%> 
    <tr>
      <td><%=bean.getActiveCode() %></td>
      <td><%=bean.getTrainingYear() %></td>
      <td><%=MapKit.getValueFromMap(bean.getUsageStatus(), ActiveCodeInfoConfig.USAGE_STATUS_MAP) %></td>
      <td><%=bean.getProvince() %></td>
      <td><%=bean.getCity() %></td>
      <td><%=bean.getRegion()%></td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='ActiveCodeInfoManage.do'">
  	<%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>


</body>
<script type="text/javascript">
$(document).ready(function() {
	var url='<%=REGION_URL%>/region/RegionAjaxList.do';
	$.getScript(url,function(data){
		getProvinceNameList('country','province','<%=province%>');
		getCityNameList('provinceDummy','city','<%=city%>');
		getRegionNameList('cityDummy','region','<%=region%>');
	});
});
</script>
</html>