<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%
    	Boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    %>
    <%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>首页</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link href="<%=STATIC_URL %>css/manager.css" rel="stylesheet">
</head>
<body class="manager-bg">
	<header class="g-header">
	</header>
	<div class="mask" id="ul-mask" style="display: none"></div>
	<div class="g-container u-wrap">
		<section class="manager-item"><a href="<%=YUN_CLASS_URL %>admin/course/normal/index"><span class="icon-1 img-box"></span></a></section>
		<section class="manager-item"><a href="<%=YUN_CLASS_URL %>admin/teacher"><span class="icon-2 img-box"></span></a></section>
		<section class="manager-item"><a href="<%=YUN_CLASS_URL %>admin/user"><span class="icon-3 img-box"></span></a></section>
		<section class="manager-item"><a href="TrainingClassInfoList.do"><span class="icon-6 img-box"></span></a></section>
	</div>

	<!-- 移动端解决方案 必要-->
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.10.1/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/manager.js"></script>
</body>
</html>