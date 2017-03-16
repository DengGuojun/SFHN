<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.lpmas.framework.config.*"%>
<%@ page import="com.lpmas.framework.bean.*"%>
<%@ page import="com.lpmas.framework.page.*"%>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.web.*"%>
<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据导入</title>
</head>
<body>
	<form method="post" enctype="multipart/form-data"
		action="TeacherInfoImport.do" style="float: left; margin-right: 50px;">
		<div class="form-group">
			<div class="col-xs-2 form-label">师资导入</div>
			<div class="col-xs-10">
				<div class="row">
					<div class="col-xs-8">
						<input type="file" id="file" name="file" value="上传"
							class="form-control"><input type="submit" value="提交" />
					</div>
				</div>
			</div>
		</div>
	</form>
	<form method="post" enctype="multipart/form-data"
		action="DeclareInfoImport.do" style="float: left; margin-right: 50px;">
		<div class="form-group">
			<div class="col-xs-2 form-label">培育对象导入</div>
			<div class="col-xs-10">
				<div class="row">
					<div class="col-xs-8">
						<input type="file" id="file" name="file" value="上传"
							class="form-control"><input type="submit" value="提交" />
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>