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
	List<TrainingClassTargetBean> list = (List<TrainingClassTargetBean>)request.getAttribute("TrainingClassTargetList");
	List<TrainingOrganizationInfoBean> trainingOrgAllList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrgAllList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	Map<Object,TrainingOrganizationInfoBean> trainingOrgMap =(Map<Object,TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrgMap");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>培训班指标列表管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">培训班指标列表</p>
<form name="formSearch" method="post" action="TrainingClassTargetList.do">
<div class="search_form">
  	<em class="em1">省：</em>
    <input type="text" name="province" id="province" value="<%=ParamKit.getParameter(request, "province", "") %>" size="20"/>
    <em class="em1">市：</em>
    <input type="text" name="city" id="city" value="<%=ParamKit.getParameter(request, "city", "") %>" size="20"/>
    <em class="em1">区：</em>
    <input type="text" name="region" id="region" value="<%=ParamKit.getParameter(request, "region", "") %>" size="20"/>
    培训机构：<select id="organizationId" name="organizationId"  checkStr="培训机构;txt;false;;100">
		<option value=""></option>
		<%
			for (TrainingOrganizationInfoBean trainingOrgBean : trainingOrgAllList) {
		%>
		<option value="<%=trainingOrgBean.getOrganizationId()%>" <%=ParamKit.getIntParameter(request, "organizationId", 0) == trainingOrgBean.getOrganizationId() ? "selected" : ""%>><%=trainingOrgBean.getOrganizationName()%></option>
		<%
			}
		%>
	</select> 
    培育年份：<select id="trainingYear" name="trainingYear" >
		<option value=""></option>
		<%
			for (Integer year : trainingOrganizationInfoHelper.getTrainingYearList()) {
		%>
		<option value="<%=year%>" <%=ParamKit.getParameter(request, "trainingYear", "").equals(String.valueOf(year)) ? "selected" : ""%>><%=year%></option>
		<%
			}
		%>
	</select>
	培育类型：<select name="trainingType" >
		<option value=""></option>
        <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
		<option value="<%=statusBean.getStatus()%>"  <%=(ParamKit.getIntParameter(request, "trainingYear", 0) == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
		<%} %>
   </select>
    <input type="hidden" name="status" id="status" value="<%=Constants.STATUS_VALID %>"/>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
      <th>培育年份</th>     
      <th>培育类型</th>
      <th>政府机构</th>
      <th>培育机构</th>
      <th>培育指标</th>
      <th>操作</th>
    </tr>
    <%
    for(TrainingClassTargetBean bean:list){%> 
    <tr>
      <td><%=bean.getTrainingYear() %></td>
      <td><%=MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></td>
      <td><%=bean.getProvince()%><%=bean.getCity() %><%=bean.getRegion()%></td>
      <td><%=bean.getOrganizationId() > 0 ? trainingOrgMap.get(bean.getOrganizationId()).getOrganizationName() : ""%></td>
      <td><%=bean.getTargetQuantity() %></td>
      <td align="center">
      	<a href="/sfhn/TrainingClassTargetManage.do?targetId=<%=bean.getTargetId()%>&readOnly=true">查看</a>
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %> 
       |<a href="/sfhn/TrainingClassTargetManage.do?targetId=<%=bean.getTargetId()%>&readOnly=false">修改</a>
       <%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='TrainingClassTargetManage.do'">
  	<%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>


</body>
</html>