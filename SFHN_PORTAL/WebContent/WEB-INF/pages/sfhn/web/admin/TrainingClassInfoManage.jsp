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
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%
	TrainingClassInfoBean bean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingOrganizationInfoBean trainingOrgBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgBean");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    List<IndustryBeltInfoBean> industryBeltInfoList  = (List<IndustryBeltInfoBean>)request.getAttribute("IndustryBeltInfoList");
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
        <link href="<%=STATIC_URL %>css/bootstrap-datetimepicker.min.css" rel="stylesheet">
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
                    <span class="u-title"><a href="javascript:history.back();"><i class="icon-return"></i></a>新开班申请</span>
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
                    <form class="form-box col-xs-8" id="formData" method="post"  onsubmit="javascript:return formOnSubmit();">
                    <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
   		            <input type="hidden" name="classStatus" value="<%=bean.getClassStatus() %>"/>
   		            <input type="hidden" name="syncStatus" value="<%=bean.getSyncStatus() %>"/>
   		            <input type="hidden" name="acceptStatus" value="<%=bean.getAcceptStatus() %>"/>
   		            <input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训班名</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="className" placeholder="请输入培训班名称" value="<%=bean.getClassName() %>" checkStr="培训班名称;txt;true;;200">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培育机构</div>
                                <div class="col-xs-10">
                                    <span><%=trainingOrgBean.getOrganizationName()%></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训年份</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="trainingYear" checkStr="培训年份;txt;true;;200">
					                     <option></option>
					                     <%for(Integer year : trainingOrganizationInfoHelper.getTrainingYearList()){ %>
											<option value="<%=year%>"  <%=bean.getTrainingYear().equals(String.valueOf(year)) ? "selected" : ""%>><%=year %></option>
										<%} %>
					                  </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培育类型</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="trainingType" checkStr="培育类型;txt;true;;200" >
					                    <option></option>
					                    <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
											<option value="<%=statusBean.getStatus()%>"  <%=(bean.getTrainingType() == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
										<%} %>
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">开班人数</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" placeholder="请输入开班人数"  name="classPeopleQuantity" value="<%=bean.getClassPeopleQuantity() >0 ? bean.getClassPeopleQuantity() : ""%>" checkStr="开班人数;num;true;;20"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训班类型</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="classType" checkStr="培训班类型;txt;true;;200" <%=bean.getClassId() >0 ? "readonly": "" %>>
					                    <option></option>
					                    <%for(StatusBean<Integer,String> statusBean : TrainingClassInfoConfig.CLASS_TYPE_LIST){ %>
											<option value="<%=statusBean.getStatus()%>"  <%=(bean.getClassType() == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
										<%} %>
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">产业带类型</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="industryBeltId" checkStr="产业带;txt;true;;200" >
					                    <option></option>
					                    <%for(IndustryBeltInfoBean industryBeltInfobean : industryBeltInfoList){ %>
											<option value="<%=industryBeltInfobean.getBeltId()%>"  <%=(bean.getIndustryBeltId() == industryBeltInfobean.getBeltId()) ? "selected" : "" %>><%=industryBeltInfobean.getBeltName() %></option>
										<%} %>
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">省</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="selectProvince"  id="selectProvince" onchange="showCity()" checkStr="省;txt;true;;200" >
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">市</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="selectCity"  id="selectCity" onchange="showRegion()" checkStr="市;txt;false;;200" >
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">区</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="selectRegion"  id="selectRegion" onchange="setRegion()" checkStr="区;txt;false;;200" >
					                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">课程开始时间</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" id="datetimepicker1" value="<%=DateKit.formatTimestamp(bean.getTrainingBeginTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%>"  checkStr="课程开始时间;date;true;;100">
                                    <input type="hidden" name="trainingBeginTime" id="trainingBeginTime" value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">课程结束时间</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" id="datetimepicker2" value="<%=DateKit.formatTimestamp(bean.getTrainingEndTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%>"  checkStr="课程结束时间;date;true;;100">
                                    <input type="hidden" name="trainingEndTime" id="trainingEndTime" value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训时间</div>
                                <div class="col-xs-10">
                                    <input class="form-control" type="text" placeholder="请输入培训时间" name="trainingTime" value="<%=bean.getTrainingTime() %>" checkStr="培训时间;txt;true;;100">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">行程安排</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" placeholder="请输入行程安排"  name="trainingSchedule" value="<%=bean.getTrainingSchedule() %>" checkStr="行程安排;txt;true;;200"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">报名截止时间</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" id="datetimepicker3" value="<%=DateKit.formatTimestamp(bean.getRegistrationEndTime(), DateKit.DEFAULT_DATE_FORMAT)%>"  checkStr="报名截止日期;date;true;;100">
                                    <input type="hidden" name="registrationEndTime" id="registrationEndTime" value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培育工程申报表</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input class="form-control" type="file" id="file1" class="up-file" onchange="up('file1',<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>);" >
                                        </div>
                                    </div>
                                </div>
                                <p class="bg-info pd-15" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>_txt" style="display:none">已上传</p>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">项目年度实施计划</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input class="form-control" type="file" id="file2" class="up-file" onchange="up('file2',<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>);" >
                                        </div>
                                    </div>
                                </div>
                                <p class="bg-info pd-15" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>_txt" style="display:none">已上传</p>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训指南</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input class="form-control" type="file" id="file3" class="up-file" onchange="up('file3',<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>);">
                                        </div>
                                    </div>
                                </div>
                                <p class="bg-info pd-15" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>_txt" style="display:none">已上传</p>
                            </div>
                            <input type="hidden" name="province" id="province" value="<%=bean.getProvince()%>"/>
						    <input type="hidden" name="city" id="city" value="<%=bean.getCity()%>"/>
                            <input type="hidden" name="region" id="region" value="<%=bean.getRegion()%>"/>
                            <input type="hidden" name="declareFormFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM).getFilePath(): "" %>" checkStr="培育工程申报表;file;true;;1000"/>
				            <input type="hidden" name="annualPlanFilePath" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) != null ?  fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN).getFilePath(): "" %>" checkStr="项目年度实施计划;file;true;;1000"/>
				            <input type="hidden" name="trainingGuideFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE).getFilePath() : ""%>" checkStr="培训指南;file;true;;1000"/>
				            <input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM).getFileId(): "" %>" />
				            <input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) != null ?  fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN).getFileId(): "" %>" />
				            <input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE).getFileId() : ""%>" />
                        </div>
                        <div class="text-center btn-wrp">
                        	<button type="button" class="btn first btn-default btn-mini" id="cancel">取消</button>
                            <button class="btn btn-success btn-mini">提交</button>
                        </div>
                    </form>
                    
                </div>
            </div>
        </div>
		<div class="main-bd">
          <div class="loading-mask loading-box dn"><img src="<%=STATIC_URL %>images/loading.gif"></div>
       </div>
    </div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<!-- 日期格式化及语言格式 -->
<script src="<%=STATIC_URL %>js/moment.js"></script><!-- 必要 -->
<script src="<%=STATIC_URL %>js/zh-cn.js"></script><!-- 中文显示需要 -->
<!-- 日期插件 -->
<script src="<%=STATIC_URL %>js/bootstrap-datetimepicker.min.js"></script><!-- 必要 -->
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
$(function(){
    mScroller();
    $('#datetimepicker1,#datetimepicker2,#datetimepicker3').datetimepicker({
        locale: 'zh-cn',
        format: 'YYYY-MM-DD HH:mm',
    });
})
$(document).ready(function() {
	showProvince();
	<%-- var existDeclareForm = <%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM) !=null%>
	if(existDeclareForm){
		$("#"+<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>+"_txt").show();
	}
	var existAnnualPlan = <%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) !=null%>
	if(existAnnualPlan){
		$("#"+<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>+"_txt").show();
	}
	var existTrainingGuide = <%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null%>
	if(existTrainingGuide){
		alert("yes");
		$("#"+<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>+"_txt").show();
	} --%>
	$("#cancel").click(function() {
		if(confirm("确定要放弃保存吗?")){
			window.history.back();
		 }
	});
});
function formOnSubmit(){
	if(checkForm('formData')){
		//验证通过后，日期选择框补0
		var datetimepicker1 = $("#datetimepicker1").val();
		if(datetimepicker1 != "" && datetimepicker1.indexOf("00:00:00") == -1){
			$("#trainingBeginTime").val(datetimepicker1  + ":00");
		}
		var datetimepicker2 = $("#datetimepicker2").val();
		if(datetimepicker2 != "" && datetimepicker2.indexOf("00:00:00") == -1){
			$("#trainingEndTime").val(datetimepicker2  + ":00");
		}
		var datetimepicker3 = $("#datetimepicker3").val();
		if(datetimepicker3 != "" && datetimepicker3.indexOf("00:00:00") == -1){
			$("#registrationEndTime").val(datetimepicker3  + ":00");
		}
		return true;
	}else{
		return false;
	}
}   
function up(fileInput,fileType) {
	$('.loading-box').removeClass('dn');
    var file_data = $('#'+fileInput)[0].files; // for multiple files
    var existId = $('#'+fileType+'_id').val();
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
            url: 'FileInfoUpload.do?fileId='+existId,
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.code == '1'){
                		var dataObj=eval("("+data.message+")");
            			$("#"+fileType).val(dataObj[0]);
            			alert("上传成功");
            			$('#'+fileType+'_txt').show();
                }else{
                		alert(data.message);
                }
                $('.loading-box').addClass('dn');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
        
    }
}
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
	      	var fixProvince = $("#province").val();
	      	if(fixProvince == item.provinceName){
	      		sel.append("<option value = '"+item.provinceId+"' selected>"+item.provinceName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.provinceId+"'>"+item.provinceName+"</option>");
	      	}
	    };
	    if(fixProvince != ""){
	    		showCity();
	    }
	    
    } else{
   		sel.empty();  
    }
}

function showCity(){
	var provinceId = $("#selectProvince").val();
	var provinceName = $("#selectProvince  option:selected").text();
	$("#province").val(provinceName);
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
	var sel = $("#selectCity");  
	sel.empty();  
	sel.append("<option value = ''></option>");
	var items=data.content.cityList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixCity = $("#city").val();
	      	if(fixCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.cityId+"'>"+item.cityName+"</option>");
	      	}
	    };
	    if(fixCity != ""){
	    		showRegion();
	    }
    } else{
   		sel.empty();  
    }
}

function showRegion(){
	var cityId = $("#selectCity").val();
	var cityName = $("#selectCity  option:selected").text();
	$("#city").val(cityName);
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
	var sel = $("#selectRegion");  
	sel.empty();  
	sel.append("<option value = ''></option>");
	var items=data.content.regionList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixRegion = $("#region").val();
	      	if(fixRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      		isFix = true;
	      	}else{
	      		sel.append("<option value = '"+item.regionId+"'>"+item.regionName+"</option>");
	      	}
	    };
    } else{
   		sel.empty();  
    }
}

function setRegion(){
	var regionName = $("#selectRegion  option:selected").text();
	$("#region").val(regionName);
}
</script>
</html>