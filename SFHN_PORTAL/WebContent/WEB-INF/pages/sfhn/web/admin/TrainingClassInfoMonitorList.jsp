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
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>实时监控</span>
                    <div class="dropdown fr ">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        个人设置
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="http://passport.1haowenjian.cn/user/UserInfoManage.do">个人设置</a></li>
                            <li><a href="http://passport.1haowenjian.cn/user/Logout.do?target=http://zhxn.1haowenjian.cn/sfhn/admin/Index.do">退出</a></li>
                        </ul>
                    </div>
                </div>
                <div class="main-bd">
                 <form>
                    <div class="search-box">
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                <input type="hidden" id="queryCity"  name="queryCity" value="<%=queryCity !=null ? queryCity : "" %>"/>
                                <input type="hidden" id="queryRegion" name="queryRegion" value="<%=queryRegion !=null ? queryRegion : "" %>"/>
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
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育类型:</div>
                                    <div class="col-xs-6">
                                         <select class="form-control" name="trainingType">
          	                             <option value="">全部</option>
          	                            <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
					                    <option value="<%=statusBean.getStatus()%>"  <%=ParamKit.getIntParameter(request, "trainingType", 0)==statusBean.getStatus() ? "selected" : ""%>><%=statusBean.getValue() %></option>
			                            <%} %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育机构:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="organizationId">
                                       <option value="">全部</option>
                                       <%for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList){ %>
					                  <option value="<%=trainingOrganizationInfoBean.getOrganizationId()%>" <%=ParamKit.getIntParameter(request, "organizationId", 0)==trainingOrganizationInfoBean.getOrganizationId()? "selected" : ""%>><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
			                          <%} %>
                                      </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-6 col-xs-offset-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6"><input type="checkbox" id="isBroadcasting" name="isBroadcasting" value="<%=Constants.STATUS_VALID %>" <%=(ParamKit.getIntParameter(request, "isBroadcasting", 0)==Constants.STATUS_VALID)?"checked":"" %>> 已连线现场</div>
                                    <div class="col-xs-6">
                                        <button class="btn btn-success btn-block">查询</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    </form>
                    <section class="section-wrp">
                    	<div class="alert alert-info" role="alert">
                    	  有<%=isBroadcastingList.size() %>个培训班 正在开课
                    	</div>
                        <table class="table table-base table-odd">
                        	<tr>
                        		<th>培育班名称</th>
                        		<th>对象人数</th>
                        		<th>培育机构</th>
                        		<th>地区</th>
                        		<th>开课时间</th>
                        	</tr>
                        	<%for(TrainingClassInfoEntityBean bean : list) {%>
                            <tr>
                                <td><%=bean.getClassName() %></td>
                                <td><%=bean.getClassPeopleQuantity()%></td>
                                <td>
                                   <%=bean.getOrganizationName()%>
                                </td>
                                <td><%=bean.getProvince()%> <%=bean.getCity()%> <%=bean.getRegion()%></td>
                                <td><%if(isBroadcastingList.contains(bean.getEduClassId())){ %>
		          	                <a href="TrainingClassInfoMonitor.do?classId=<%=bean.getClassId()%>">实时监控</a>
		          	                <%}else{ %>
		          	                                         未连线
		          	                <%} %></td>
                            </tr>   
                            <%} %>                       
                        </table>
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