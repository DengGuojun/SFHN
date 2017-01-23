<%@page import="com.lpmas.sfhn.portal.config.TrainingClassUserConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.constant.user.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	FileInfoBean fieldTrainingPlan = (FileInfoBean)request.getAttribute("FieldTrainingPlan");
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
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
	<title>培训班详情</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL%>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="g-nav mb18">
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>'"><span>学员名单</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassPlanManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>年度计划</span></div>
				<div class="nav-item w-5-1 fl selected"><span>集中培训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>田间实训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='ExpertInfoList.do?classId=<%=classInfoBean.getClassId()%>'"><span>跟踪服务</span></div>
			</div>
			<input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">教材封面及目录（扫描件）</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file1" class="up-file" onchange="up('file1',<%=FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG).getFileId()%>'">下载</button></div>
								<%}else if(!isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">认定信息采集表</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file2" class="up-file" onchange="up('file2',<%=FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM).getFileId()%>'">下载</button></div>
								<%}else if(!!isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">参观考察基地简介</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file3" class="up-file" onchange="up('file3',<%=FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">学员培训台帐</span>
							<span class="fr cont-tips">
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET).getFileId()%>'">下载</button></div>
								<%}else{%>
									未上传
								<% } %>
							</span>	
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">学员信息汇总表</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file5" class="up-file" onchange="up('file5',<%=FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET).getFileId()%>'">下载</button></div>
								<%}else if(!isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_INFO_SUMMARY_SHEET).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">班委会成员名单</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file6" class="up-file" onchange="up('file6',<%=FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">学员考勤表</span>
							<span class="fr cont-tips">
							<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST) !=null || !isGovernment){ %>
							<div class="btn btn-view"><button onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST%>'">查看</button></div></span>
							<%}else {%>
								未上传
							<%} %>
						</span>
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">培训现场照片</span>
							<span class="fr cont-tips">
							<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_PHOTO) !=null || !isGovernment){ %>
							<div class="btn btn-view"><button onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_PHOTO%>'">查看</button></div></span>
							<%}else {%>
								未上传
							<%} %>
						</span>
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">学员满意度测评汇总表</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file9" class="up-file" onchange="up('file9',<%=FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getFileId()%>'">下载</button></div>
								<%}else if(!isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">考试考核材料（成绩汇总表）</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file10" class="up-file" onchange="up('file10',<%=FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">跟踪管理协议</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file11" class="up-file" onchange="up('file11',<%=FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">跟踪服务对接表</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file12" class="up-file" onchange="up('file12',<%=FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_SERVICE_SHEET).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">培育资金使用计划</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file13" class="up-file" onchange="up('file13',<%=FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">身份证复印件(扫描上传)</span>
							<span class="fr cont-tips">
							<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY) !=null || !isGovernment){ %>
							<div class="btn btn-view"><button onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY%>'">查看</button></div></span>
							<%}else {%>
								未上传
							<%} %>
						</span>
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">认定申报表</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file15" class="up-file" onchange="up('file15',<%=FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">典型学员</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file16" class="up-file" onchange="up('file16',<%=FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">培训班总结</span>
							<span class="fr cont-tips">
								<%if(!isGovernment) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file17" class="up-file" onchange="up('file17',<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY %>);"/><button>上传</button></div>
								<%} %>
								<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY) !=null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getFileId()%>'">下载</button></div>
								<%}else if(isGovernment){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getFileId(): "" %>" />
					</div>
				</div>
			</section>
		</div>
		<div class="loading-box" style="display: none; ">
			<img src="<%=STATIC_URL %>images/loading-icon.png" class="loading-icon rotate">
		</div>
	</div>
</body>
<script type="text/javascript">
function up(fileInput,fileType) {
	$(".loading-box").show();
    var file_data = $('#'+fileInput)[0].files; // for multiple files
    var existId = $('#'+fileType+'_id').val();
    var classId = $("#classId").val();
    var infoType = '<%=InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO%>';
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
        		url: 'FileInfoUpload.do?fileId='+existId+'&infoId1='+classId+'&infoType='+infoType+"&fileType="+fileType,
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.code == '1'){
                		alert("上传成功");
                		var url = "TrainingClassCentralizedManage.do?classId=" + classId;
                		window.location.href= url
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
</script>
</html>