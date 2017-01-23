<%@page import="com.lpmas.sfhn.portal.config.TrainingClassInfoConfig"%>
<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<%@ page import="com.lpmas.sfhn.portal.business.TrainingClassStatusHelper"  %>
<%
	List<TrainingClassInfoEntityBean> list = (List<TrainingClassInfoEntityBean>)request.getAttribute("ClassInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	boolean isOwner = (Boolean)request.getAttribute("isOwner");
	int userId = (Integer)request.getAttribute("UserId");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
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
	<title>培训班列表</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<%if(!isOwner) {%>
			<form>
			<div class="select-box">
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
			            <option value="">全部</option>
			               <%for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList){ %>
								<option value="<%=trainingOrganizationInfoBean.getOrganizationId()%>" <%=ParamKit.getIntParameter(request, "organizationId", 0)==trainingOrganizationInfoBean.getOrganizationId()? "selected" : ""%>><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
						  <%} %>
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
			<div class="main-section mb18">
				<div class="main-hd pd43 little">
						<h4>共<%=PAGE_BEAN.getTotalRecordNumber() %>条</h4>
				</div>
			</div>
			<%for(TrainingClassInfoEntityBean bean : list) {%>
			<div class="main-section mb18">
				<div class="main-bd">
					<div class="table-title">
						<div class="tb-top"><%=bean.getClassName() %></div>
						<div class="tb-foot">
							<span><%=MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></span>
							<span class="font-extra"><%=bean.getClassPeopleQuantity() %>人</span>
						</div>
					</div>
					<table class="table-cont">
						<tbody>
							<tr>
								<td onclick="javascript:location.href='TrainingClassInfoProcess.do?classId=<%=bean.getClassId()%>'">查看开班流程</td>
								<td onclick="javascript:location.href='TrainingClassAcceptProcess.do?classId=<%=bean.getClassId()%>'">查看验收流程</td>
							</tr>
							<tr>
								<td onclick="javascript:location.href='TrainingClassPlanManage.do?classId=<%=bean.getClassId()%>'">开班年度计划</td>
								<td onclick="javascript:location.href='TrainingClassUserList.do?classId=<%=bean.getClassId()%>'">班级学员名单</td>
							</tr>
							<tr>
								<td onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=bean.getClassId()%>'">集中培训资料</td>
								<td onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=bean.getClassId()%>'">田间实训资料</td>
							</tr>
							<tr>
								<td onclick="javascript:location.href='ExpertInfoList.do?classId=<%=bean.getClassId()%>'">专家跟踪服务</td>
								<td onclick="javascript:location.href='lpmas://xiangnongyun/live/hls.lpmas.com/yunketang/<%=bean.getClassId() %>/playlist.m3u8'">查看开班监控</td>
							</tr>
						</tbody>
					</table>
					</div>
				</div>
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
}

function rejectClass(classId){
	if(confirm("确定需要驳回该审批")){
		var url = "TrainingClassInfoAdminApprove.do?action=<%=TrainingClassInfoConfig.APPROVE_ACTION_FAIL %>&classId="+classId ;
		window.location.href= url
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