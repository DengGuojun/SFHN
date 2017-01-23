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
<%
	TrainingClassInfoBean bean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingOrganizationInfoBean trainingOrgBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgBean");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
%>

<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="hotcss" content="initial-dpr=1,max-width=720">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" /> 
	<title>开班申请</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
	
	<link href="<%=STATIC_URL %>css/mobiscroll.animation.css" rel="stylesheet">
    <link href="<%=STATIC_URL %>css/mobiscroll.icons.css" rel="stylesheet">
    <link href="<%=STATIC_URL %>css/mobiscroll.frame.css" rel="stylesheet">
    <link href="<%=STATIC_URL %>css/mobiscroll.scroller.css" rel="stylesheet">
    <script src="<%=STATIC_URL %>js/mobiscroll.core.js"></script>
    <script src="<%=STATIC_URL %>js/mobiscroll.frame.js"></script>
    <script src="<%=STATIC_URL %>js/mobiscroll.scroller.js"></script>

    <script src="<%=STATIC_URL %>js/mobiscroll.util.datetime.js"></script>
    <script src="<%=STATIC_URL %>js/mobiscroll.datetimebase.js"></script>
    <script src="<%=STATIC_URL %>js/mobiscroll.datetime.js"></script>

    <script src="<%=STATIC_URL %>js/mobiscroll.i18n.zh.js"></script>
</head>
<body class="gray-bg pg-bottom">
	<div class="container">
		<form id="formData" class="form-horizontal" method="post" novalidate="novalidate" data-widget-cid="widget-1" onsubmit="javascript:return formOnSubmit();">
	    <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
   		<input type="hidden" name="classStatus" value="<%=bean.getClassStatus() %>"/>
   		<input type="hidden" name="province" value="<%=bean.getProvince() %>"/>
   		<input type="hidden" name="city" value="<%=bean.getCity() %>"/>
   		<input type="hidden" name="region" value="<%=bean.getRegion() %>"/>
   		<input type="hidden" name="syncStatus" value="<%=bean.getSyncStatus() %>"/>
   		<input type="hidden" name="acceptStatus" value="<%=bean.getAcceptStatus() %>"/>
   		<input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
		<div class="container-bd mt0">
			<div class="main-section mb18">
				<div class="main-bd mb18">
					<ul>
						<li>
							<label class="u-title"><span>培训班名称：</span></label>
							<div class="u-content">
								<div class="input-item">
									<input type="text" name="className" placeholder="请输入培训班名称" value="<%=bean.getClassName() %>" checkStr="培训班名称;txt;true;;200"/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>培训机构：</span></label>
							<div class="u-content">
								<div class="input-item">
									<span><%=trainingOrgBean.getOrganizationName()%></span>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>培育年份：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt"><%=bean.getTrainingYear() %></span>
									<select class="form-select" name="trainingYear" checkStr="培育年份;txt;true;;200">
					                     <option></option>
					                     <%for(Integer year : trainingOrganizationInfoHelper.getTrainingYearList()){ %>
											<option value="<%=year%>"  <%=bean.getTrainingYear().equals(String.valueOf(year)) ? "selected" : ""%>><%=year %></option>
										<%} %>
					                  </select>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>培育类型：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt"><%=bean.getTrainingType() !=0 ? MapKit.getValueFromMap(bean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) : "" %></span>
									<select class="form-select" name="trainingType" checkStr="培育类型;txt;true;;200" >
					                    <option></option>
					                    <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
											<option value="<%=statusBean.getStatus()%>"  <%=(bean.getTrainingType() == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
										<%} %>
					                </select>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>开班人数：</span></label>
							<div class="u-content">
								<div class="input-item">
									<input type="text" placeholder="请输入开班人数"  name="classPeopleQuantity" value="<%=bean.getClassPeopleQuantity() >0 ? bean.getClassPeopleQuantity() : ""%>" checkStr="开班人数;num;true;;20"/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>培训班类型：</span></label>
							<div class="u-content select-box">
								<div class="select-item">
									<span class="select-txt"><%=bean.getClassType() !=0 ? MapKit.getValueFromMap(bean.getClassType(), TrainingClassInfoConfig.CLASS_TYPE_MAP) : "" %></span>
									<select class="form-select" name="classType" checkStr="培训班类型;txt;true;;200" >
					                    <option></option>
					                    <%for(StatusBean<Integer,String> statusBean : TrainingClassInfoConfig.CLASS_TYPE_LIST){ %>
											<option value="<%=statusBean.getStatus()%>"  <%=(bean.getClassType() == statusBean.getStatus()) ? "selected" : "" %>><%=statusBean.getValue() %></option>
										<%} %>
					                </select>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>课程开始时间：</span></label>
							<div class="u-content">
								<div class="input-item input-date">
									<input type="text" id="datetimepicker1"  value="<%=DateKit.formatTimestamp(bean.getTrainingBeginTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) == null ?"":DateKit.formatTimestamp(bean.getTrainingBeginTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%>"  checkStr="课程开始时间;date;true;;100"/>
									<input type="hidden" name="trainingBeginTime" id="trainingBeginTime" value=""/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>课程结束时间：</span></label>
							<div class="u-content">
								<div class="input-item input-date">
									<input type="text" id="datetimepicker2"  value="<%=DateKit.formatTimestamp(bean.getTrainingEndTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) == null ?"":DateKit.formatTimestamp(bean.getTrainingEndTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%>"  checkStr="课程结束时间;date;true;;100"/>
									<input type="hidden" name="trainingEndTime" id="trainingEndTime" value=""/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>培训时间：</span></label>
							<div class="u-content">
								<div class="input-item ">
									<input type="text" placeholder="请输入培训时间" name="trainingTime" value="<%=bean.getTrainingTime() %>" checkStr="培训时间;txt;true;;100"/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>行程安排：</span></label>
							<div class="u-content">
								<div class="input-item">
									<input type="text" placeholder="请输入行程安排"  name="trainingSchedule" value="<%=bean.getTrainingSchedule() %>" checkStr="行程安排;txt;true;;200"/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title"><span>报名截止时间：</span></label>
							<div class="u-content">
								<div class="input-item input-date">
									<input type="text" id="datetimepicker3"  value="<%=DateKit.formatTimestamp(bean.getRegistrationEndTime(), DateKit.DEFAULT_DATE_FORMAT) == null?"":DateKit.formatTimestamp(bean.getRegistrationEndTime(), DateKit.DEFAULT_DATE_FORMAT)%>"  checkStr="报名截止日期;date;true;;100"/>
									<input type="hidden" name="registrationEndTime" id="registrationEndTime" value=""/>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<dl class="info-list info-done">
				<dd class="list-item" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>_txt" style="display:none"><span class="list-title">培育工程表</span><span class="fr list-tap-done">已上传</span></dd>
				<dd class="list-item" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>_txt"  style="display:none"><span class="list-title">项目年度实施计划表</span><span class="fr list-tap-done">已上传</span></dd>
				<dd class="list-item" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>_txt" style="display:none"><span class="list-title">培训指南</span><span class="fr list-tap-done">已上传</span></dd>
			</dl>
			<div class="mb18 upload-file-box">
				<a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file1" class="up-file" onchange="up('file1',<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>);" ><button>上传培育工程项目申报表</button></a>
				<a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file2" class="up-file" onchange="up('file2',<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>);"  ><button>上传项目年度实施计划表</button></a>
				<a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file3" class="up-file" onchange="up('file3',<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>);" ><button>上传培训指南</button></a>
				<input type="hidden" name="declareFormFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM).getFilePath(): "" %>" checkStr="培育工程申报表;file;true;;1000"/>
				<input type="hidden" name="annualPlanFilePath" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) != null ?  fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN).getFilePath(): "" %>" checkStr="项目年度实施计划;file;true;;1000"/>
				<input type="hidden" name="trainingGuideFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE).getFilePath() : ""%>" checkStr="培训指南;file;true;;1000"/>
				<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM).getFileId(): "" %>" />
				<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_ANNUAL_PLAN %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN) != null ?  fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN).getFileId(): "" %>" />
				<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_GUIDE %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE).getFileId() : ""%>" />
			</div>
			<div class="bg-tips">
				<div class="bg-tips-hd">大文件可以在电脑端下载</div>
				<div class="bg-tips-ft">zhxn.1haowenjian.cn</div>
			</div>
			<div class="fixed-bottom pd-btn-wrp fixed-width">
			<div class="btn btn-build w-1-1">
				<%if(bean.getClassId() > 0){ %>
				<button>编辑开班申请</button>
				<%}else{ %>
				<button>新建开班申请</button>
				<%} %>
			</div>
		</div>
		</div>
		</form>
		<div class="loading-box" style="display: none; ">
			<img src="<%=STATIC_URL %>images/loading-icon.png" class="loading-icon rotate">
		</div>
	</div>
</body>
<script type="text/javascript">
$(function () {
    $('#datetimepicker1,#datetimepicker2,#datetimepicker3').mobiscroll().datetime({
        theme: 'default',
        mode: 'scroller',
        display: 'bottom',
        lang: 'zh',
        minDate: new Date(2012, 3, 10, 9, 22),
        maxDate: new Date(2066, 7, 30, 15, 44),
        stepMinute: 1
    });
})
$(document).ready(function() {
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
	$(".loading-box").show();
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
                $(".loading-box").hide();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
        
    }
}
//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});
</script>
</html>