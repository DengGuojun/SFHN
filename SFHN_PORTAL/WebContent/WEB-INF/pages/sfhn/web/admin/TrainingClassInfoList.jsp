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
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.TrainingClassStatusHelper"  %>
<%
	List<TrainingClassInfoEntityBean> list = (List<TrainingClassInfoEntityBean>)request.getAttribute("ClassInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	int userId = (Integer)request.getAttribute("UserId");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
	String userName = (String)request.getAttribute("UserName");
	Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
    int trainingYear = ParamKit.getIntParameter(request, "trainingYear", 0);
    int trainingType = ParamKit.getIntParameter(request, "trainingType", 0);
    String classStatusSelection = ParamKit.getParameter(request, "classStatusSelection", "");
    String acceptStatusSelection = ParamKit.getParameter(request, "acceptStatusSelection", "");
    String openStatus = ParamKit.getParameter(request, "openStatus", "");
    int industryBeltId = ParamKit.getIntParameter(request, "industryBeltId", 0);
    Integer organizationId = ParamKit.getIntParameter(request, "organizationId", 0);
    List<IndustryBeltInfoBean> industryBeltInfoList = (List<IndustryBeltInfoBean>)request.getAttribute("IndustryBeltInfoList");
    
%>
<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
        <title>湘农科教云管理后台</title>

        <!-- Bootstrap -->
        <link href="<%=STATIC_URL %>css/bootstrap.min.css" rel="stylesheet">
        <link href="<%=STATIC_URL %>css/style_web.css" rel="stylesheet">
        <script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
        <!--[if lt IE 9]>
          <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
  <body>
  <div class="container">
        <div class="row">
            <%@ include file="../../../include/web/navigation.jsp" %>
            <div class="center-side">
                <div class="main-hd">
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>培训班审批</span>
                    <div class="dropdown fr ">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        个人设置
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="http://passport.1haowenjian.cn/user/UserInfoManage.do">个人设置</a></li>
                            <li><a href="http://passport.1haowenjian.cn/user/Logout.do">退出</a></li>
                        </ul>
                    </div>
                </div>
                <div class="main-bd">
			       <form id="searchForm">
                    <div class="search-box">
                    <input type="hidden" id="queryCity"  name="queryCity" value="<%=queryCity !=null ? queryCity : "" %>"/>
           		    <input type="hidden" id="queryRegion" name="queryRegion" value="<%=queryRegion !=null ? queryRegion : "" %>"/>
                        <%if(isGovernment) {%>
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-3">区域:</div>
                                    <div class="col-xs-3">
                                         <select class="form-control" name="selectProvince" id="selectProvince" onchange="showCity()">
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                         <select class="form-control" name="selectCity" id="selectCity" onchange="showRegion()">
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                         <select class="form-control" name="selectRegion" id="selectRegion" onchange="setRegion()">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%} %>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">审批状态:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="classStatusSelection" id="classStatusSelection" >
                                             <option value="">请选择</option>
					                         <%for(StatusBean<String, String> status : TrainingClassInfoConfig.CLASS_STATUS_SELECTION_LIST){ %>
						                      <%if(!isGovernment) {%>
						                      <option value="<%=status.getStatus() %>" <%=classStatusSelection.equals(status.getStatus()) ? "selected" : ""%>><%=status.getValue() %></option>
						                      <%} else if (isGovernment && !status.getStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)){%>
						                      <option value="<%=status.getStatus() %>" <%=classStatusSelection.equals(status.getStatus()) ? "selected" : ""%>><%=status.getValue() %></option>
						                      <%} %>
				                             <%}%>	
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育年份:</div>
                                    <div class="col-xs-6">                                     
                                        <select class="form-control" name="trainingYear" id="trainingYear" >
                                            <option value="">请选择</option>
					                         <%for(Integer infoYear : trainingOrganizationInfoHelper.getTrainingYearList()){ %>
						                     <option value="<%=infoYear %>" <%=trainingYear == infoYear? "selected" : ""%>><%=infoYear %></option>
				                             <%}%>	
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">开班状态:</div>
                                    <div class="col-xs-6">
                                         <select class="form-control" name="openStatus" id="openStatus" >
                                            <option value="">请选择</option>
					                         <%for(StatusBean<String, String> status : TrainingClassInfoConfig.OPEN_STATUS_LIST){ %>
						                     <option value="<%=status.getStatus() %>" <%=openStatus.equals(status.getStatus())? "selected" : ""%>><%=status.getValue() %></option>
				                             <%}%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">验收状态:</div>
                                    <div class="col-xs-6">
                                         <select class="form-control" name="acceptStatusSelection" id="acceptStatusSelection" >
                                            <option value="">请选择</option>
					                         <%for(StatusBean<String, String> status : TrainingClassInfoConfig.ACCEPT_STATUS_SELECTION_LIST){ %>
						                     <option value="<%=status.getStatus() %>" <%=acceptStatusSelection.equals(status.getStatus()) ? "selected" : ""%>><%=status.getValue() %></option>
				                             <%}%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育类型:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="trainingType" id="trainingType" >
                                            <option value="">请选择</option>
					                         <%for(StatusBean<Integer, String> status : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
						                     <option value="<%=status.getStatus() %>" <%=trainingType == status.getStatus()? "selected" : ""%>><%=status.getValue() %></option>
				                             <%}%>	
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%if(isGovernment){ %>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育机构:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="organizationId" id="organizationId" >
			                            <option value="">全部</option>
			                            <%for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList){ %>
								        <option value="<%=trainingOrganizationInfoBean.getOrganizationId()%>" <%=organizationId == trainingOrganizationInfoBean.getOrganizationId()? "selected" : ""%>><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
						                <%} %>
		                                </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%} %>
                         <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">产业带:</div>
                                    <div class="col-xs-6">
                                         <select class="form-control" name="industryBeltId" id="industryBeltId" >
			                            <option value="">全部</option>
			                            <%for(IndustryBeltInfoBean industryBeltInfoBean : industryBeltInfoList){ %>
								        <option value="<%=industryBeltInfoBean.getBeltId()%>" <%=industryBeltId == industryBeltInfoBean.getBeltId()? "selected" : ""%>><%=industryBeltInfoBean.getBeltName() %></option>
						                <%} %>
		                                </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-offset-6 col-xs-6">
                            <div class="form-group">
	                            <div class="row">
	                            	<div class="col-xs-6">
	                                    <button  class="btn btn-success btn-block">查询</button>
	                                </div>
	                                <div class="col-xs-6">
	                                    <a class="btn btn-default btn-block" onclick="javascript:location.href='TrainingClassInfoExport.do?queryCity=<%=queryCity %>&queryRegion=<%=queryRegion%>&classStatusSelection=<%=classStatusSelection%>&acceptStatusSelection=<%=acceptStatusSelection%>&openStatus=<%=openStatus%>&trainingYear=<%=trainingYear%>&trainingType=<%=trainingType%>&organizationId=<%=organizationId%>&industryBeltId=<%=industryBeltId%>&queryCity=<%=queryCity%>&queryRegion=<%=queryRegion%>'">
										导出</a>
	                                </div>
	                            </div>
                            </div>
                        </div>
                    </div>
                     <p class="pd-15 bg-info mt-16">
                    		查询结果： <span><%=(PAGE_BEAN.getCurrentPageNumber() * PAGE_BEAN.getPageSize()) > PAGE_BEAN.getTotalRecordNumber() ? PAGE_BEAN.getTotalRecordNumber() : (PAGE_BEAN.getCurrentPageNumber() * PAGE_BEAN.getPageSize())%></span>
                    		/<span><%=PAGE_BEAN.getTotalRecordNumber() %></span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="acceptRecordOnly" onclick="filterAcceptRecord()" <%=acceptStatusSelection.equals(TrainingClassInfoConfig.ACCEPT_STATUS_APPROVED) ? "checked" : "" %>/> 仅看已验收
                    </p>
                </form>
                    <section class="section-wrp">
                    <div id="tableExcel">  
                        <table class="table table-base table-odd">
                            <tr>
                                <th>培育班名称</th>
                                <th>培育类型</th>
                                <th>类型</th>
                                <th>地区</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            <%for(TrainingClassInfoEntityBean bean : list) {%>
                            <tr>
                                <td><a href="TrainingClassUserList.do?classId=<%=bean.getClassId()%>"><%=bean.getClassName() %></a></td>
                                <td>
                                    <div><%=MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></div><div><%=bean.getClassPeopleQuantity() %>人</div>
                                </td>
                                <td><%=MapKit.getValueFromMap(bean.getClassType(), TrainingClassInfoConfig.CLASS_TYPE_MAP) %></td>
                                <td><%=bean.getProvince() %>-<%=bean.getCity() %>-<%=bean.getRegion() %></td>
                                <%if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_OPENED) && (DateKit.getCurrentTimestamp().after(bean.getTrainingEndTime()))){ %>
                                <td>培训结束</td>
                                <%}else{ %>
                                <td><%=MapKit.getValueFromMap(bean.getClassStatus(), TrainingClassInfoConfig.CLASS_STATUS_MAP) %></td>
                                <%} %>
                                <%if(isGovernment){ %>
                                <td><a href="TrainingClassInfoProcess.do?classId=<%=bean.getClassId()%>" class="ml-10">开班审批</a><a href="TrainingClassAcceptProcess.do?classId=<%=bean.getClassId()%>" class="ml-10">验收审批</a></td>
                                <%}else{ %>
                                <td><%if(!bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_OPENED)){ %><a href="TrainingClassInfoOpen.do?classId=<%=bean.getClassId()%>" class="ml-10">开班</a><%} %><a href="TrainingClassInfoProcess.do?classId=<%=bean.getClassId()%>" class="ml-10">完善信息</a></td>
                                <%} %>
                            </tr>
                            <%} %>
                        </table>
                      </div>
                    </section>
                    <!-- 分页栏 -->
	               <%@ include file="../../../include/web/page.jsp"%>
                </div>
            </div>
        </div>
	</div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script>
mScroller();
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
function filterAcceptRecord(){
	if ($('#acceptRecordOnly').is(':checked')) {
		$("#acceptStatusSelection").find("option").eq(4).attr("selected","selected")
	}else{
		$("#acceptStatusSelection").find("option").eq(0).attr("selected","selected")
	}
	$("#searchForm").submit();
	
}

//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});
</script>
</html>