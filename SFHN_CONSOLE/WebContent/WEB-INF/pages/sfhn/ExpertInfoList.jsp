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
	List<ExpertInfoBean> list = (List<ExpertInfoBean>)request.getAttribute("ExpertInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>

<%@ include file="../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专家列表管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">专家列表</p>
<form name="formSearch" method="post" action="ExpertInfoList.do">
<div class="search_form">
	专家名称：
    <input type="text" name="expertName" id="expertName" value="<%=ParamKit.getParameter(request, "expertName", "") %>" size="20"/>
    <em class="em1">有效状态：</em>
    <select name="status" id="status">
    	<%
    	int status = ParamKit.getIntParameter(request, "status", Constants.STATUS_VALID);
    	for(StatusBean<Integer, String> statusBean:Constants.STATUS_LIST){ %>
          <option value="<%=statusBean.getStatus() %>" <%=(statusBean.getStatus()==status)?"selected":"" %>><%=statusBean.getValue() %></option>
        <%} %>
    </select>
    <input name="" type="submit" class="search_btn_sub" value="查询"/>
  </div>
  <table width="100%" border="0"  cellpadding="0" class="table_style">
    <tr>
    	  <th>专家ID</th>  
      <th>专家名称</th>  
      <th>专家类别</th>   
      <th>专家专业</th>
      <th>操作</th>
    </tr>
    <%
    for(ExpertInfoBean bean:list){%> 
    <tr>
    	  <td><%=bean.getExpertId() %></td>
      <td><%=bean.getExpertName() %></td>
      <td><%=bean.getExpertType() %></td>
      <td><%=bean.getExpertMajor()%></td>
      <td align="center">
      	<a href="/sfhn/ExpertInfoManage.do?expertId=<%=bean.getExpertId()%>&readOnly=true">查看</a>
      	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.UPDATE)){ %> 
       | <a href="/sfhn/ExpertInfoManage.do?expertId=<%=bean.getExpertId()%>&readOnly=false">修改</a>
       <%} %>
       | <a href="/sfhn/TrackingInfoList.do?expertId=<%=bean.getExpertId()%>">跟踪信息列表</a>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
	<%if(adminUserHelper.hasPermission(SfhnResource.SFHN_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='ExpertInfoManage.do'">
  	<%} %>	
</li>
<%@ include file="../include/page.jsp" %>
</ul>


</body>
</html>