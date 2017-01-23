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
<%		
	String queryProvince = ParamKit.getParameter(request, "queryProvince", "");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	String organizationId = ParamKit.getParameter(request, "organizationId", "");
	boolean isOwner = (Boolean)request.getAttribute("isOwner");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String trainingYear = ParamKit.getParameter(request, "trainingYear", DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR));
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");

	int youngFarmerTarget = (Integer)request.getAttribute("youngFarmerTarget");
	int productFarmerTarget = (Integer)request.getAttribute("productFarmerTarget");
	int technicalFarmerTarget = (Integer)request.getAttribute("technicalFarmerTarget");
	int serviceFarmerTarget = (Integer)request.getAttribute("serviceFarmerTarget");
	int leaderFarmerTarget = (Integer)request.getAttribute("leaderFarmerTarget");
	int generalTarget = (Integer)request.getAttribute("generalTarget");
	
	int youngFarmerCount = (Integer)request.getAttribute("youngFarmerCount");
	int productFarmerCount = (Integer)request.getAttribute("productFarmerCount");
	int technicalFarmerCount = (Integer)request.getAttribute("technicalFarmerCount");
	int serviceFarmerCount = (Integer)request.getAttribute("serviceFarmerCount");
	int leaderFarmerCount = (Integer)request.getAttribute("leaderFarmerCount");
	int generalConut = (Integer)request.getAttribute("generalConut");
	
	String youngFarmerPercent = (String)request.getAttribute("youngFarmerPercent");
	String productFarmerPercent = (String)request.getAttribute("productFarmerPercent");
	String technicalFarmerPercent = (String)request.getAttribute("technicalFarmerPercent");
	String serviceFarmerPercent = (String)request.getAttribute("serviceFarmerPercent");
	String leaderFarmerPercent = (String)request.getAttribute("leaderFarmerPercent");
	String generalPercent = (String)request.getAttribute("generalPercent");
%>
<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>培训班统计</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg">
	<div class="container pg-bottom">
		<div class="container-bd mt0">
		<%if(!isOwner) {%>
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
				<div class="w-3-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select  name="selectCity" id="selectCity" onchange="showRegion()">
	       			</select>
				</div>
				<div class="w-3-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select  name="selectRegion" id="selectRegion" onchange="setRegion()">
	       			</select>
				</div>
				<div class="w-3-1 select-item fl">
					<span class="select-txt">全部</span>
					<i class="icon-more"></i>
					<select class="form-control" name="organizationId" id="organizationId" >		              
		            </select>
				</div>				
			</div>
			<div class="mg-wrp text-right">
				<a class="btn btn-success">
					<button >查询</button>
				</a>
			</div>
			</form>
		<%} %>
			<div class="pd43 g-process mb18">
				<div class="process-p">
					<span class="process-p-name">整体完成情况</span><span class="process-p-discount fr"><%=generalPercent %>%<b>(<%=generalConut %>/<%=generalTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="generalSituation"></div>
				</div>
			</div>
			<div class="pd43 g-process">
				<div class="process-p">
					<span class="process-p-name">生产经营型</span><span class="process-p-discount fr"><%=productFarmerPercent %>%<b>(<%=productFarmerCount %>/<%=productFarmerTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="productFarmerSituation"></div>
				</div>
			</div>
			<div class="pd43 g-process">
				<div class="process-p">
					<span class="process-p-name">专业技能型</span><span class="process-p-discount fr"><%=technicalFarmerPercent %>%<b>(<%=technicalFarmerCount %>/<%=technicalFarmerTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="technicalFarmerSituation"></div>
				</div>
			</div>
			<div class="pd43 g-process">
				<div class="process-p">
					<span class="process-p-name">社会服务型</span><span class="process-p-discount fr"><%=serviceFarmerPercent %>%<b>(<%=serviceFarmerCount %>/<%=serviceFarmerTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="serviceFarmerSituation"></div>
				</div>
			</div>
			<div class="pd43 g-process">
				<div class="process-p">
					<span class="process-p-name">青年农场主</span><span class="process-p-discount fr"><%=youngFarmerPercent %>%<b>(<%=youngFarmerCount %>/<%=youngFarmerTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="youngFarmerSituation"></div>
				</div>
			</div>
			<div class="pd43 g-process">
			   <div class="process-p">
					<span class="process-p-name">经营带头人</span><span class="process-p-discount fr"><%=leaderFarmerPercent %>%<b>(<%=leaderFarmerCount %>/<%=leaderFarmerTarget %>)</b></span>
				</div>
				<div class="process-line">
					<div class="process-line-done" id="leaderFarmerSituation"></div>
				</div>
			</div>
			
			<form name="formYear" method="post" action="">
		     <input type="hidden" name="trainingYear" id="trainingYear" value=""/>
		       <%for(String[] condArray :COND_LIST){ %>
		     <input type="hidden" name="<%=condArray[0] %>" id="<%=condArray[0] %>" value="<%=condArray[1] %>"/><%} %>
	       </form>
			<div class="g-btn-menu fixed-bottom">
				<a class="btn btn-prev"><button onclick="javascript:goYear('formYear',<%=Integer.parseInt(trainingYear)-1%>);">上一年</button></a>
				<%if(trainingYear.equals(DateKit.formatTimestamp(DateKit.getCurrentTimestamp(), DateKit.REGEX_YEAR))){ %>
				<a class="btn btn-next fr btn-disabled"><button>下一年</button></a>
			    <%}else{ %>
			    <a class="btn btn-next fr"><button onclick="javascript:goYear('formYear',<%=Integer.parseInt(trainingYear)+1%>);">下一年</button></a>
			    <%} %>
			</div>
		</div>
	</div>

</body>
<script>
$(document).ready(function() {
	showProvince();
	var generalSituation = document.getElementById('generalSituation');  
	generalSituation.style.width="<%=generalPercent %>"+"%"; 
	var productFarmerSituation = document.getElementById('productFarmerSituation');  
	productFarmerSituation.style.width="<%=productFarmerPercent %>"+"%"; 
	var technicalFarmerSituation = document.getElementById('technicalFarmerSituation');  
	technicalFarmerSituation.style.width="<%=technicalFarmerPercent %>"+"%"; 
	var serviceFarmerSituation = document.getElementById('serviceFarmerSituation');  
	serviceFarmerSituation.style.width="<%=serviceFarmerPercent %>"+"%"; 
	var youngFarmerSituation = document.getElementById('youngFarmerSituation');  
	youngFarmerSituation.style.width="<%=youngFarmerPercent %>"+"%"; 
	var leaderFarmerSituation = document.getElementById('leaderFarmerSituation');  
	leaderFarmerSituation.style.width="<%=leaderFarmerPercent %>"+"%"; 
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

//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});

function goYear(formId, trainingYear) {
	document.forms[formId].trainingYear.value = trainingYear;
	document.forms[formId].submit();
}
</script>
</html>