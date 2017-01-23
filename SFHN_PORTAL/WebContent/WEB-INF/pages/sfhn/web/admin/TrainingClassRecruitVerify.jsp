<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
String logContent = (String) request.getAttribute("LogContent");
Integer fileId = (Integer)request.getAttribute("fileId");
TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("classInfoBean");
%>
<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>湘农科教云管理后台</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
	  <div class="container-bd">
	     <section class="r-info mb18 pd43">
				 <%if(!logContent.isEmpty()){ %>
				 <%String[] sourceStrArray = logContent.split("!"); %>
				<div class="r-info-hd">名单<%=sourceStrArray[0].split(",", 2)[1]%></div>
				<div class="d-table r-info-box">
				<%for(int i=1;i<sourceStrArray.length;++i){%>
				<%String[] str = sourceStrArray[i].split(",", 3); %>
		          <div class="d-cell"><%=str[0]%></div>
				  <div class="d-cell"><%=str[1]%></div>
				  <div class="d-cell r-info-account"><%=str[2]%></div>
		         </div>
		         <%} %>
		        <%} %>
		</section>
		<div class="container-bd mt0">
			<dl class="info-list mb18">
				<dd class="list-item"><a>完整学员名单<i class="icon-arrow" onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileId %>'"></i></a></dd>
			</dl>
	    </div>
	    <div class="btn-group pt44">
	    <%if(classInfoBean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH) ){ %>
				<div class="btn btn-success btn-disabled"><button>完成招生</button></div>
				<div class="btn btn-success fr btn-disabled"><button>继续招生</button></div>
		<%} else{%>
		<form method="post" action="TrainingClassRecruitVerify.do" id="TrainingClassRecruitVerify">
	             <input type="hidden" id="classId"  name="classId" value="<%=classInfoBean.getClassId() %>"/>
	             <input type="hidden" id="isFinish"  name="isFinish" value=""/>
				<div class="btn btn-success"><button type="button" onclick="submitFinish();">完成招生</button></div>
				<div class="btn btn-success fr"><button type="button" onclick="submitGo();">继续招生</button></div>
		</form>
		<%} %>
	   </div>
	 </div>
	</div>
</body>
<script type="text/javascript">
function submitFinish() {
	$("#isFinish").val("1");
       document.getElementById("TrainingClassRecruitVerify").submit();
       
 }

function submitGo() {
	$("#isFinish").val("0");
       document.getElementById("TrainingClassRecruitVerify").submit();
 }

</script>
</html>