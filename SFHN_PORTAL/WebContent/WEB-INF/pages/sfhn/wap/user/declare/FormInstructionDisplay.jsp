<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../../include/header.jsp" %>
<%
	String instruction = (String)request.getAttribute("Instruction");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>填表说明</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
</head>
<body>
    <div class="g-h2 f-tac"><a href="javascript:history.go(-1);" class="back-a"><i class="back-point"></i></a>填表说明</div>
    <div class="placeHolder"></div>
	<div class="g-pb65 show-1">
    	<section class="info-table">
		    <p style="padding: 10px 20px; ">
		        <%=instruction %>
		    </p>
        </section>
    </div>
</body>
</html>