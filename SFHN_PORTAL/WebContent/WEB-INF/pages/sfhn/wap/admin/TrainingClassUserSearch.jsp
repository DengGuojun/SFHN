<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>

<%@ include file="../../../include/header.jsp" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>招生筛选</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
	<div class="container-bd">
			<div class="main-section">
				<div class="main-bd mb18">
					<ul>
						<li>
						   <form method="post" action="TrainingClassUserSearch.do" id="TrainingClassUserSearch">
							<label class="u-title"><span>身份证号码：</span></label>
							<div class="u-content">
								<div class="input-item">
									<input type="text" id="identityNumber" name="identityNumber" placeholder="请输入身份证号码" />
								</div>
							</div>
							</form>
						</li>
					</ul>
				</div>
			</div>
			<div class="pd43 pt0">
				<div class="btn btn-upload w-1-1"><button onclick="submitSearch();">提交</button></div>
			</div>
		</div>
   </div>
</body>
<script type="text/javascript">
function submitSearch() {
	var identityNumber = $("#identityNumber").val();
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if (identityNumber == ''){
		alert("请输入身份证号");
		return;
	}
	if(reg.test(identityNumber) === false)
	{
	    alert("身份证输入不合法");
	    return ;
	}
     document.getElementById("TrainingClassUserSearch").submit();
       
 }

</script>
</html>