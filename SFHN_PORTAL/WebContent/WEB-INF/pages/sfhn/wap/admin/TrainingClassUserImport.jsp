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
<%
List<TrainingClassInfoBean> list = (List<TrainingClassInfoBean>)request.getAttribute("trainingClassInfoList");
String organizationName = (String)request.getAttribute("organizationName");
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
	<title>导入学员台账</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
</head>
<body class="gray-bg pg-bottom">
	<div class="container">
		<div class="container-bd mt0">
			<div class="main-section ">
				<div class="main-bd ">
					<ul>
						<li>
							<label class="u-title"><span>培训机构：</span></label>
							<div class="u-content">
								<div class="input-item">
									<span><%=organizationName %></span>
								</div>
							</div>
						</li>
						<li  class="mb18">
							<label class="u-title"><span>培训班：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt">请选择培训班</span>
									<select class="form-select" name="trainingClassId" id="trainingClassId" checkStr="培训班;txt;true;;200">
									     <option value="">请选择培训班</option>
					                     <% int trainingClassId = ParamKit.getIntParameter(request, "trainingClassId", 0);
			                              for(TrainingClassInfoBean trainingClassInfoBean : list){ %>
											<option value="<%=trainingClassInfoBean.getClassId()%>" <%=(trainingClassInfoBean.getClassId()==trainingClassId)?"selected":""%> ><%=trainingClassInfoBean.getClassName() %></option>
										<%} %>
					                  </select>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>			
				<form method="post"  enctype="multipart/form-data" action="TrainingClassUserImport.do" id="TrainingClassUserImport">
				<div class="mb18">
			   <a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file" name="file" class="up-file" onchange="submitSheet();" ><button>导入学员台账</button></a>
			   </div>
		       </form>	
		       <form method="post"  enctype="multipart/form-data" action="TrainingClassUserDetailImport.do" id="TrainingClassUserDetailImport">
				<div class="mb18">
			   <a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file" name="file" class="up-file" onchange="submitForm();"><button >导入学员信息表</button></a>
			   </div>
		       </form>	
		   </div>
		   </div>			
</body>
<script type="text/javascript">
$(document).ready(function() {
	 $(".select-item select").trigger('change');
});
function submitSheet() {
	var orgAction = $("#TrainingClassUserImport").attr('action');
	var trainingClassId = $("#trainingClassId").val();   
	$("#TrainingClassUserImport").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassUserImport").submit();
 }

//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});

function submitForm() {
	var orgAction = $("#TrainingClassUserDetailImport").attr('action');
	var trainingClassId = $("#trainingClassId").val();   
	$("#TrainingClassUserDetailImport").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassUserDetailImport").submit();
 }

</script>
</html>