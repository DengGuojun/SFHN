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
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.console.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
    AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	List<NationalCertificationBean> list = (List<NationalCertificationBean>)request.getAttribute("NationalCertificationList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
%>  

<%@ include file="../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>证书管理</title>
<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
<script type='text/javascript' src="<%=STATIC_URL %>/js/jquery.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/common.js"></script>
<script type='text/javascript' src="<%=STATIC_URL %>/js/ui.js"></script>
</head>
<body class="article_bg">
<p class="article_tit">证书列表</p>
<form name="formSearch" method="post" action="NationalCertificationList.do">
<div class="search_form">
  	<em class="em1">证书名称：</em>
    <input type="text" name="certificateName" id="certificateName" value="<%=ParamKit.getParameter(request, "certificateName", "") %>" size="20"/>
    <em class="em1">状态：</em>
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
      <th>证书ID</th>
      <th>证书名称</th>
      <th>有效状态</th>
      <th>操作</th>
    </tr>
    <%
    for(NationalCertificationBean bean:list){%> 
    <tr>
      <td><%=bean.getCertificateId() %></td>
      <td><%=bean.getCertificateName() %></td>
      <td><%=Constants.STATUS_MAP.get(bean.getStatus())%></td>
      <td align="center">
      	<a href="/sfhn/NationalCertificationManage.do?certificateId=<%=bean.getCertificateId()%>&readOnly=true">查看</a> 
      	<%if(adminUserHelper.hasPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.UPDATE)){ %>
       |<a href="/sfhn/NationalCertificationManage.do?certificateId=<%=bean.getCertificateId()%>&readOnly=false">修改</a>
       <%} %>
      </td>
    </tr>	
    <%} %>
  </table>
</form>
<ul class="page_info">
<li class="page_left_btn">
<%if(adminUserHelper.hasPermission(DeclareInfoResource.DECLARE_INFO, OperationConfig.CREATE)){ %>
  	<input type="button" name="button" id="button" value="新建" onclick="javascript:location.href='NationalCertificationManage.do'">
<%} %>   	
</li>
<%@ include file="../../include/page.jsp" %>
</ul>
</body>
</html>