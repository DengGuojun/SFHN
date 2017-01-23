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
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%
	List<TrainingClassInfoEntityBean> list = (List<TrainingClassInfoEntityBean>)request.getAttribute("ClassInfoList");
	List<Integer> isBroadcastingList = (List<Integer>)request.getAttribute("isBroadcastingList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int userId = (Integer)request.getAttribute("UserId");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
%>

<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<!-- saved from url=(0033)http://yun.lpmas.com/admin/thread -->
<html class=""><!--<![endif]--><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="renderer" content="webkit">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta content="c0c6d687cf0f11fc3f67955a6bba6d69c211075e" name="csrf-token">
  <title>培训班管理</title>
  <link href="<%=STATIC_URL %>css/bootstrap.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/common.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/other.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/admin.css" rel="stylesheet">
  <link href="<%=STATIC_URL %>css/admin_v2.css" rel="stylesheet">
  <link rel="stylesheet" media="screen" href="<%=STATIC_URL %>css/es-icon.css">
  <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
  <script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
    <!--[if lt IE 9]>

  <![endif]-->

</head>
<body>
  <div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="Index.do">智慧湘农云管理中心</a>
      </div>
      <div class="navbar-collapse collapse">
        <ul class=" navbar-nav navbar-right">
          <li class="dropdown" style="margin-top: 15px;">
                <a href="javascript:;" style="text-decoration:none;cursor:default" class="dropdown-toggle"> <%=request.getAttribute("UserName") %></a>
              	<span>|</span>
              	<a href="http://passport.1haowenjian.cn/user/UserInfoManage.do" class="dropdown-toggle" data-toggle="dropdown">账号设置</a>
              	<span>|</span>
              	<a href="http://passport.1haowenjian.cn/user/Logout.do">退出</a>
           </li>
        </ul>

      </div><!--/.navbar-collapse -->
    </div>
  </div>
  
  <div class="container">
    <div class="row">
        <div class="col-md-10">
          <div class="page-header clearfix">
            <h1 class="pull-left">实时监控</h1>
            <div class="pull-right">
            </div>
          </div>
<div class="well well-sm">
  <form class="form-inline">
    <div>
    <div class="form-group">
        区域:
        <select class="form-control" name="selectProvince" id="selectProvince" onchange="showCity()">
        </select>
        <select class="form-control" name="selectCity" id="selectCity" onchange="showRegion()">
        </select>
        <select class="form-control" name="selectRegion" id="selectRegion" onchange="setRegion()">
        </select>
        <input type="hidden" id="queryCity"  name="queryCity" value="<%=queryCity !=null ? queryCity : "" %>"/>
        <input type="hidden" id="queryRegion" name="queryRegion" value="<%=queryRegion !=null ? queryRegion : "" %>"/>
     </div>
      <span class="divider"></span>
      <div class="form-group">
          培育类型:
          <select class="form-control" name="trainingType">
          	<option value="">全部</option>
          	<%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
					<option value="<%=statusBean.getStatus()%>"  <%=ParamKit.getIntParameter(request, "trainingType", 0)==statusBean.getStatus() ? "selected" : ""%>><%=statusBean.getValue() %></option>
			<%} %>
          </select>
      </div>
      <div class="form-group">
          <input name="isBroadcasting" type="checkbox" value="<%=Constants.STATUS_VALID %>" <%=ParamKit.getIntParameter(request, "isBroadcasting", Constants.STATUS_NOT_VALID)==Constants.STATUS_VALID ? "checked":"" %>>已连线现场
      </div>
      </div>
        <div style="margin-top: 10px"> 
        <div class="form-group">
            培育机构:
            <select class="form-control" name="organizationId">
            <option value="">全部</option>
               <%for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList){ %>
					<option value="<%=trainingOrganizationInfoBean.getOrganizationId()%>" <%=ParamKit.getIntParameter(request, "organizationId", 0)==trainingOrganizationInfoBean.getOrganizationId()? "selected" : ""%>><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
			  <%} %>
            </select>
        </div>
            <span class="divider"></span>
            <button class="btn btn-primary" type="submit">查询</button>
        </div>
  </form>
</div>
  <div id="thread-table-container">
  	<span>有<%=isBroadcastingList.size() %>个培育班正在开课</span>
    <table class="table table-striped table-hover">
      <thead>
        <tr>
          <th width="15%">培训班名称</th>
          <th width="10%">对象人数</th>
          <th width="10%">培育机构</th>
           <th width="8%">培育类型</th>
          <th width="10%">地区</th>
          <th width="20%">开课时间</th>      
          <th width="15%">操作</th>
        </tr>
      </thead>
            <tbody>
	            <%for(TrainingClassInfoEntityBean bean : list) {%>
		      	 <tr>
		          <td><%=bean.getClassName() %></td>
		          <td><%=bean.getClassPeopleQuantity()%></td>
		          <td><%=bean.getOrganizationName()%></td>
		          <td><%=MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP)%></td>
		          <td><%=bean.getProvince()%> <%=bean.getCity()%> <%=bean.getRegion()%></td>
		          <td><%=bean.getTrainingTime()%></td>
		          <td>
		          	<%if(isBroadcastingList.contains(bean.getEduClassId())){ %>
		          	<a href="TrainingClassInfoMonitor.do?classId=<%=bean.getClassId()%>">实时监控</a>
		          	<%}else{ %>
		          	未连线
		          	<%} %>
		          </td>
		        </tr>
		      	<%} %>
            </tbody>
      </table>
    <div class="mbm">

    </div>
    <div style="text-align: right;display:inline-block;float:right">
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
  </div>
      </div>
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
	    }
	    if(isQuery){
	    		showRegion();
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
</script>
</html>