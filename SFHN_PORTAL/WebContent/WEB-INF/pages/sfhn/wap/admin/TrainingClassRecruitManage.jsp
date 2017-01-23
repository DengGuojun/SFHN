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
List<TrainingOrganizationInfoBean> list = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
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
	<title>上传学员名单</title>
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
						<li  class="mb18">
							<label class="u-title"><span>培训机构：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt">请选择培训机构</span>
									<select class="form-select" name="organizationId" id="organizationId" checkStr="培训机构;txt;true;;200" onchange="selectClass()">
									      <option value=""></option>
					                     <% int organizationId = ParamKit.getIntParameter(request, "organizationId", 0);
			                              for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : list){ %>
											<option value="<%=trainingOrganizationInfoBean.getOrganizationId() %>" <%=(trainingOrganizationInfoBean.getOrganizationId()==organizationId)?"selected":""%> ><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
										<%} %>
					                  </select>
								</div>
							</div>
						</li>
						<li  class="mb18">
							<label class="u-title"><span>培训班：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt">请选择培训班</span>
									<select class="form-select" name="trainingClassId" id="trainingClassId" checkStr="培训班;txt;true;;200">
					                  </select>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>			
				<form method="post"  enctype="multipart/form-data" action="TrainingClassRecruitManage.do" id="TrainingClassRecruitManage">
				<div class="mb18">
			   <a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file" name="file" class="up-file" onchange="submitForm();" ><button>上传学员名单附件</button></a>
			   </div>
		       </form>	
		   </div>
		   </div>			
</body>
<script type="text/javascript">
//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});

function submitForm() {
	var orgAction = $("#TrainingClassRecruitManage").attr('action');
	var trainingClassId = $("#trainingClassId").val();   
	$("#TrainingClassRecruitManage").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassRecruitManage").submit();
 }

function selectClass(){
	var organizationId=$("#organizationId").val();  
	if(organizationId != null){
		var params={  
		        'organizationId':organizationId  
	    };  
	    $.ajax({
	        type: 'get',
	        url: "/sfhn/admin/TrainingClassJsonList.do",
	        data: params,
	        dataType: 'json',
	        success: function(data){
		      	var sel2 = $("#trainingClassId");  
	      		sel2.empty();  
		      	if(data==null) {
	      		 	sel2.append("<option value =''> </option>");
	          	}else {
	          		var items=data.result;
		    	    		if(items!=null) {
		    	    	  	sel2.append("<option value =''> </option>");
		        	  	for(var i =0;i<items.length;i++) {
		          		var item=items[i];
		    	      		sel2.append("<option value = '"+item.classId+"'>"+item.className+"</option>");
		           	};
		        } else{
		       		sel2.empty();  
		          }
	          	}
		      	
	        },
	        error: function(){
	            return;
	        }
	    });
	}
} 
</script>
</html>