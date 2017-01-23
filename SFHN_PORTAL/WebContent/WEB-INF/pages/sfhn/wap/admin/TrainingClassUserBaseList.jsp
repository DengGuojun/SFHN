<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.constant.user.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.TrainingClassStatusHelper"  %>
<%@page import="com.lpmas.sfhn.portal.config.*"%>
<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
    List<DeclareReportBean> list = (List<DeclareReportBean>)request.getAttribute("DeclareList");
    Map<Integer, String> industryTypeMap = (Map<Integer, String>)request.getAttribute("IndustryTypeMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	String queryProvince = ParamKit.getParameter(request, "queryProvince", "");
	String organizationId = ParamKit.getParameter(request, "organizationId", "");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
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
	<title>学员信息库</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<form>
			<div class="select-box">
			    <input type="hidden" id="queryProvince"  name="queryProvince" value="<%=queryProvince !=null ? queryProvince : "" %>"/>
				<input type="hidden" id="queryCity"  name="queryCity" value="<%=queryCity !=null ? queryCity : "" %>"/>
           		<input type="hidden" id="queryRegion" name="queryRegion" value="<%=queryRegion !=null ? queryRegion : "" %>"/>
				<div class="select-item" style="display:none">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select name="selectProvince" id="selectProvince" onchange="showCity()">
	       			</select>
				</div>
				<div class="w-5-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select  name="selectCity" id="selectCity" onchange="showRegion()">
	       			</select>
				</div>
				<div class="w-5-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select  name="selectRegion" id="selectRegion" onchange="setRegion()">
	       			</select>
				</div>
				<div class="w-5-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select class="form-control" name="organizationId" id="organizationId" >
		            </select>
				</div>
				<div class="w-5-1 select-item fl">
				 <span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select class="form-control" name="trainingYear" id="trainingYear" >
					 <% Integer trainingYear = ParamKit.getIntParameter(request, "trainingYear", 0);%>
					 <%for(Integer InfoYear : trainingOrganizationInfoHelper.getTrainingYearList()){ %>
						<option value="<%=InfoYear %>" <%=trainingYear == InfoYear? "selected" : ""%>><%=InfoYear %></option>
				      <%}%>	              
		            </select>
				</div>
				<div class="w-5-1 select-item fl">
				 <span class="select-txt">是否参与培训班</span>
					<i class="icon-more"></i>
					<select class="form-control" name="isTrain" id="isTrain" >
					  <% Integer isTrain = ParamKit.getIntParameter(request, "isTrain", 1);%>
					 <%for(StatusBean<Integer,String> trainStatus : TrainingClassUserConfig.SELECT_LIST){ %>
						<option value="<%=trainStatus.getStatus() %>" <%=trainStatus.getStatus() == isTrain? "selected" : ""%>><%=trainStatus.getValue()%></option>
				      <%}%>	            
		            </select>
				</div>
			</div>
			<div class="mg-wrp text-right">
				<a class="btn btn-success">
					<button >查询</button>
				</a>
			</div>
			</form>
			<%for(DeclareReportBean bean : list) {%>
			<section class="panel-wrp">
				<div class="panel-item panel-extra">
					<div class="panel-detail">
						<div class="panel-h3">
							<span><%=bean.getUserName() %></span>
							<span class="fr"><%=MapKit.getValueFromMap(isTrain, TrainingClassUserConfig.SELECT_MAP) %></span>
						</div>
						<div class="panel-tips">
						<span><%=bean.getIdentityNumber() %></span>
						<span class="fr"><%=MapKit.getValueFromMap(bean.getIndustryTypeId1(),industryTypeMap) %></span>
						</div>
						<i class="icon-arrow"  onclick="javascript:location.href='TrainingClassUserManage.do?userId=<%=bean.getUserId() %>&identityNumber=<%=bean.getIdentityNumber() %>'"></i>
					</div>
				</div>
			</section>
			<%} %>
	</div>
	<!-- 分页栏 -->
	<form name="formPage" method="post" action="">
		<input type="hidden" name="pageNum" id="pageNum" value=""/>
		<%for(String[] condArray :COND_LIST){ %>
		<input type="hidden" name="<%=condArray[0] %>" id="<%=condArray[0] %>" value="<%=condArray[1] %>"/><%} %>
	</form>
	<div class="page-num clearfix">
		<%if(PAGE_BEAN.getCurrentPageNumber()>1){ %>
		<div class="btn-page">
			<button class="u-btn btn-prev" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getPrePageNumber() %>);">上一页</button>
		</div>
		<%} %>
		<%if(PAGE_BEAN.getTotalPageNumber() >1){ %>
		<div class="select-box">
			<span class="page-txt"><%=PAGE_BEAN.getCurrentPageNumber() %> / <%=PAGE_BEAN.getTotalPageNumber() %></span>
			<select id="pageBar" onchange="jump()">
				<%for(int i=1; i<=PAGE_BEAN.getTotalPageNumber() ; i++) {%>
				<option value="<%=i%>" <%=PAGE_BEAN.getCurrentPageNumber() == i ? "selected" : "" %> >第<%=i%>页</option>
				<%} %>
			</select>
		</div>
		<%} %>
		<%if(PAGE_BEAN.getCurrentPageNumber() < PAGE_BEAN.getTotalPageNumber()){ %>
		<div class="btn-page">
			<button class="u-btn btn-next" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getNextPageNumber() %>);">下一页</button>
		</div>
		<%} %>
	</div>
	</div>
</body>
<script>
$(document).ready(function() {
	showProvince();
	$("#trainingYear").val("<%=trainingYear%>");
});
function showProvince(){
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/ProvinceList.action?jsoncallback=provinceData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function provinceData(data){
	var sel = $("#selectProvince");  
	sel.empty();  
	sel.append("<option value = ''></option>");
	var items=data.content.provinceList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixProvince = "<%=fixProvince%>";
	      	if(fixProvince == item.provinceName){
	      		sel.append("<option value = '"+item.provinceId+"' selected>"+item.provinceName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.provinceId+"'>"+item.provinceName+"</option>");
	      	}
	      	$("#selectProvince").attr("disabled","disabled");
	    };
	    showCity();
    } else{
   		sel.empty();  
    }
    	var queryProvince="<%=fixProvince%>";  
    	if(queryProvince != null){
    		var params={  
    		        'queryProvince':queryProvince  
    	    };  
    	    $.ajax({
    	        type: 'get',
    	        url: "/sfhn/admin/TrainingOrganizationJsonList.do",
    	        data: params,
    	        dataType: 'json',
    	        success: function(data){
    		      	var sel2 = $("#organizationId");  
    	      		sel2.empty();  
    		      	if(data==null) {
    	      		 	sel2.append("<option value =''>全部</option>");
    	          	}else {
    	          		var items=data.result;
    		    	    		if(items!=null) {
    		    	    	  	sel2.append("<option value =''>全部</option>");
    		        	  	for(var i =0;i<items.length;i++) {
    		          		var item=items[i];
    		          		var organizationId = "<%=organizationId%>";
    		          		if(organizationId == item.organizationId){
    		    	      		sel2.append("<option value = '"+item.organizationId+"' selected>"+item.organizationName+"</option>");
    		    	      	}else{
    		    	      		sel2.append("<option value = '"+item.organizationId+"'>"+item.organizationName+"</option>");
    		    	      	} 
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

function showCity(){
	var provinceId = $("#selectProvince").val();
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/CityList.action?provinceId="+provinceId+"&jsoncallback=cityData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });	
	
} 

function cityData(data){
	var isFix;
	var isQuery
	var sel = $("#selectCity");  
	sel.empty();  
	sel.append("<option value = ''>全部</option>");
	var items=data.content.cityList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixCity = "<%=fixCity%>";
	      	var queryCity = "<%=queryCity%>";
	      	if(fixCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      		isFix = true;
	      	}else if(queryCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      		isQuery = true;
	      	}else{
	      		sel.append("<option value = '"+item.cityId+"'>"+item.cityName+"</option>");
	      	}
	    };
	    if(isFix){
	    		$("#selectCity").attr("disabled","disabled");
	    		showRegion();
	    }else if(isQuery){
	    		showRegion();
   		}else {
   		//初始化筛选框
		$('.select-item select').each(function(){
			var val = $(this).find('option:selected').text();
			$(this).parents('.select-item').find('.select-txt').text(val);
		})
   		}
    } else{
   		sel.empty();  
    }
    	var queryProvince="<%=fixProvince%>"; 
    	if(fixCity != "" && fixCity != "null"){
    		var queryCity="<%=fixCity%>"; 
    	}else{
    		var queryCity=$("#queryCity").val(); 
    	}   	 
    	if(queryProvince != null || queryCity != null){
    		var params={  
    		        'queryProvince':queryProvince, 
    		        'queryCity':queryCity 
    	    };   
    	    $.ajax({
    	        type: 'get',
    	        url: "/sfhn/admin/TrainingOrganizationJsonList.do",
    	        data: params,
    	        dataType: 'json',
    	        success: function(data){
    		      	var sel2 = $("#organizationId");  
    	      		sel2.empty();  
    		      	if(data==null) {
    	      		 	sel2.append("<option value =''>全部</option>");
    	          	}else {
    	          		var items=data.result;
    		    	    		if(items!=null) {
    		    	    	  	sel2.append("<option value =''>全部</option>");
    		        	  	for(var i =0;i<items.length;i++) {
    		          		var item=items[i];
    		          		var organizationId = "<%=organizationId%>";
    		          		if(organizationId == item.organizationId){
    		    	      		sel2.append("<option value = '"+item.organizationId+"' selected>"+item.organizationName+"</option>");
    		    	      	}else{
    		    	      		sel2.append("<option value = '"+item.organizationId+"'>"+item.organizationName+"</option>");
    		    	      	} 
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

function showRegion(){
	var text = $("#selectCity  option:selected").text();
	if(text == "全部"){
		$("#queryCity").val("");
	}else{
		$("#queryCity").val(text);
	} 
	var cityId = $("#selectCity").val();
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/RegionList.action?cityId="+cityId+"&jsoncallback=regionData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function regionData(data){
	var isFix;
	var sel = $("#selectRegion");  
	sel.empty();  
	sel.append("<option value = ''>全部</option>");
	var items=data.content.regionList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixRegion = "<%=fixRegion%>";
	      	var queryRegion = "<%=queryRegion%>";
	      	if(fixRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      		isFix = true;
	      	}else if(queryRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.regionId+"'>"+item.regionName+"</option>");
	      	}
	    };
	    if(isFix){
	    		$("#selectRegion").attr("disabled","disabled");
	    }
    } else{
   		sel.empty();  
    }
    	var queryProvince="<%=fixProvince%>"; 
    	var queryCity=$("#queryCity").val();    	
    	if(fixRegion != "" && fixRegion != "null"){
    		var queryRegion="<%=fixRegion%>"; 
    	}else{
    		var queryRegion=$("#queryRegion").val();  
    	} 
    	if(queryProvince != null || queryCity != null || queryRegion != null){
    		var params={  
    		        'queryProvince':queryProvince, 
    		        'queryCity':queryCity, 
    		        'queryRegion':queryRegion
    	    };  
    	    $.ajax({
    	        type: 'get',
    	        url: "/sfhn/admin/TrainingOrganizationJsonList.do",
    	        data: params,
    	        dataType: 'json',
    	        success: function(data){
    		      	var sel2 = $("#organizationId");  
    	      		sel2.empty();  
    		      	if(data==null) {
    	      		 	sel2.append("<option value =''>全部</option>");
    	          	}else {
    	          		var items=data.result;
    		    	    		if(items!=null) {
    		    	    	  	sel2.append("<option value =''>全部</option>");
    		        	  	for(var i =0;i<items.length;i++) {
    		          		var item=items[i];		            		
    		            		var organizationId = "<%=organizationId%>";
    			          		if(organizationId == item.organizationId){
    			    	      		sel2.append("<option value = '"+item.organizationId+"' selected>"+item.organizationName+"</option>");
    			    	      	}else{
    			    	      		sel2.append("<option value = '"+item.organizationId+"'>"+item.organizationName+"</option>");
    			    	      	} 
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
    	//初始化筛选框
    	$('.select-item select').each(function(){
    		var val = $(this).find('option:selected').text();
    		$(this).parents('.select-item').find('.select-txt').text(val);
    	})
}

function setRegion(){
	var text = $("#selectRegion  option:selected").text();
	if(text == "全部"){
		$("#queryRegion").val("");
	}else{
		$("#queryRegion").val(text);
	} 
	var queryProvince="<%=fixProvince%>"; 
	var queryCity=$("#queryCity").val();  
	var queryRegion=$("#queryRegion").val(); 
	if(queryProvince != null || queryCity != null || queryRegion != null){
		var params={  
		        'queryProvince':queryProvince, 
		        'queryCity':queryCity, 
		        'queryRegion':queryRegion
	    };  
	    $.ajax({
	        type: 'get',
	        url: "/sfhn/admin/TrainingOrganizationJsonList.do",
	        data: params,
	        dataType: 'json',
	        success: function(data){
		      	var sel2 = $("#organizationId");  
	      		sel2.empty();  
		      	if(data==null) {
	      		 	sel2.append("<option value =''>全部</option>");
	          	}else {
	          		var items=data.result;
		    	    		if(items!=null) {
		    	    	  	sel2.append("<option value =''>全部</option>");
		        	  	for(var i =0;i<items.length;i++) {
		          		var item=items[i];		            		
		            		var organizationId = "<%=organizationId%>";
			          		if(organizationId == item.organizationId){
			    	      		sel2.append("<option value = '"+item.organizationId+"' selected>"+item.organizationName+"</option>");
			    	      	}else{
			    	      		sel2.append("<option value = '"+item.organizationId+"'>"+item.organizationName+"</option>");
			    	      	} 
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

function jump(){
	var page = $("#pageBar  option:selected").val();
	goPage('formPage',page);
}

//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});
</script>
</html>