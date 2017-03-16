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
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");;
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    int isTrain = ParamKit.getIntParameter(request, "isTrain", -1);
    int declareYear = ParamKit.getIntParameter(request, "declareYear", 0);
    String userStatus = ParamKit.getParameter(request, "userStatus", "");
    Integer declareType = ParamKit.getIntParameter(request, "declareType", 0);
    List<IndustryTypeBean> industryTypeList = (List<IndustryTypeBean>)request.getAttribute("IndustryTypeList");
    Integer industryTypeId = ParamKit.getIntParameter(request, "industryTypeId", 0);
    Integer industryId = ParamKit.getIntParameter(request, "industryId", 0);
    Integer authStatusSelection = ParamKit.getIntParameter(request, "authStatusSelection", 0);
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
        <script src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>学员信息库</span>
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
			       <form  id="searchForm">
                    <div class="search-box">
                    <input type="hidden" id="queryProvince"  name="queryProvince" value="<%=queryProvince !=null ? queryProvince : "" %>"/>
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
                                    <div class="form-label text-right col-xs-6">培育年份:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="declareYear" id="declareYear" >
                                             <option value="" ></option>
					                         <%for(Integer InfoYear : trainingOrganizationInfoHelper.getTrainingYearList()){ %>
						                     <option value="<%=InfoYear %>" <%=declareYear == InfoYear? "selected" : ""%>><%=InfoYear %></option>
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
                                        <select class="form-control" name="declareType" id="declareType" >
                                            <option value="">请选择</option>
					                         <%for(StatusBean<Integer, String> status : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
						                     <option value="<%=status.getStatus() %>" <%=declareType == status.getStatus()? "selected" : ""%>><%=status.getValue() %></option>
				                             <%}%>	
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                         <%if(isGovernment) {%>
                         <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">培育机构:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="organizationId" id="organizationId" >
		                                </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%} %>
                         <div class="col-xs-6">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-6">报班状态:</div>
                                    <div class="col-xs-6">
                                        <select class="form-control" name="isTrain" id="isTrain">
                                           <option >全部</option>
					                       <%for(StatusBean<Integer,String> trainStatus : TrainingClassUserConfig.SELECT_LIST){ %>
						                   <option value="<%=trainStatus.getStatus() %>" <%=trainStatus.getStatus() == isTrain? "selected" : ""%>><%=trainStatus.getValue()%></option>
				                            <%}%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                		
                                    <div class="form-label text-right col-xs-3"></div>
                                    <div class="col-xs-3">
                                    <!-- 
	                                    <div id="industryType">
	                                        <select class="form-control industryType" name="industryTypeId" id="industryTypeId" >
	                                            <option value="">请选择</option>
						                         <%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
							                     <option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeId == industryTypeBean.getTypeId() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
					                             <%}%>	
	                                        </select>
	                                   </div>
	                                    -->
                                    </div>
                                    <div class="col-xs-3">
                                    <!-- 
                                    	<select class="form-control industry" name="industryId" id="industryId" ></select>
		                                <input type="hidden" class="originalIndustryId" name="originalIndustryId" id="originalIndustryId" value="<%=industryId %>">
		                                 -->
                                    </div>
                                   
                                    <div class="col-xs-3">
	                                    <button  class="btn btn-success btn-block">查询</button>
	                                </div>
	                                <%if(isGovernment) {%>
	                               <!--  <div class="col-xs-3">
	                                	<a href="ActiveCodeInfoList.do">激活码使用情况</a>
	                                </div> -->
	                                <%} %>
                                </div>
                            </div>
                        </div>
                    </div>
                  <p class="pd-15 bg-info mt-16">
                    		查询结果： <span><%=(PAGE_BEAN.getCurrentPageNumber() * PAGE_BEAN.getPageSize()) > PAGE_BEAN.getTotalRecordNumber() ? PAGE_BEAN.getTotalRecordNumber() : (PAGE_BEAN.getCurrentPageNumber() * PAGE_BEAN.getPageSize())%></span>
                    		/<span><%=PAGE_BEAN.getTotalRecordNumber() %></span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="acceptRecordOnly" onclick="filterAcceptRecord()" <%=authStatusSelection.equals(Constants.STATUS_VALID) ? "checked" : "" %>/> 仅看已认定对象
                    </p>
                    <input type="hidden" id="authStatusSelection" name="authStatusSelection" value="authStatusSelection"/>
                </form>                
                    <section class="section-wrp">
                        <table class="table table-base table-odd">
                            <tr>
                                 <th>姓名</th>
                                 <th>身份证号</th>
                                 <th>是否参与培训班</th>
                                 <!-- <th>专业</th> -->
                                 <th>操作</th>
                            </tr>
                            <%for(DeclareReportBean bean : list) {%>
                            <tr>
                                <td><%=bean.getUserName() %></td>
                                <td>
                                   <%=bean.getIdentityNumber() %>
                                </td>
                                <td><%=MapKit.getValueFromMap(isTrain, TrainingClassUserConfig.SELECT_MAP) %></td>
                                <!-- <td><%=MapKit.getValueFromMap(bean.getIndustryTypeId1(),industryTypeMap) %></td> -->
                                <td><a href="TrainingClassUserManage.do?declareId=<%=bean.getDeclareId() %>">查看详情</a></td>
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

var industry;
$("#industryType").find("select.industryType").on("change",function(){
    var typeId = $(this).find("option:selected").val();
    seletCate(typeId,$(this));
});
$("#industryType").find("select.industryType").trigger('change');
function seletCate(typeId,element){
   	$.ajax({
	        type: 'get',
	        url: "/sfhn/IndustryInfoJsonList.do?typeId="+typeId,
	        dataType: 'json',
	        success: function(data){
	        		var industry=data.result;
	        		child ="";
            for(var j=0;j< industry.length;j++){
           	 	var originalId = $("#originalIndustryId").val();          
           	 	if(industry[j].industryId == originalId){
           	 		child += "<option value='" + industry[j].industryId  +"' selected>"+ industry[j].industryName + "</option>"
           	 	}else{
           	 		child += "<option value='" + industry[j].industryId  +"'>"+ industry[j].industryName + "</option>"
           	 	}
            }
            $("#industryId").html(child);
	        },
	        error: function(){
	            return;
	        }
	    });
}

function filterAcceptRecord(){
	if ($('#acceptRecordOnly').is(':checked')) {	
		$("#authStatusSelection").val("1");
	}else{
		$("#authStatusSelection").val("0");
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