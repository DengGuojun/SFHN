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
	int fieldTrainingPhotoCount = (Integer)request.getAttribute("FieldTrainingPhotoCount");
	boolean isOwner = (Boolean)request.getAttribute("IsOwner");
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
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>集中培训</span></div>
				<div class="nav-item w-5-1 fl selected" ><span>田间实训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='ExpertInfoList.do?classId=<%=classInfoBean.getClassId()%>'"><span>跟踪服务</span></div>
			</div>
			<input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">田间实训计划</span>
							<span class="fr cont-tips">
								<%if(isOwner) {%>
									<div class="btn btn-view int-upfile"><input type="file" id="file" class="up-file" onchange="up('file',<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN %>);"/><button>上传</button></div>
								<%} %>
								<%if(fieldTrainingPlan != null){%>
									<div class="btn btn-download"><button onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fieldTrainingPlan.getFileId()%>'">下载</button></div>
								<%}else if(!isOwner){%>
									未上传
								<% } %>
							</span>	
						<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN %>_id" value="<%=fieldTrainingPlan!=null ? fieldTrainingPlan.getFileId(): "" %>" />
					</div>
				</div>
			</section>
			<section class="detail-box">
				<div class="detail-wrp">
					<i class="icon-detail"></i>
					<div class="detail-cont">
						<span class="cont-title">田间实训照片</span>
							<span class="fr cont-tips">
							<%if(fieldTrainingPhotoCount >0 || isOwner){ %>
							<div class="btn btn-view"><button onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PHOTO%>'">查看</button></div></span>
							<%}else {%>
								未上传
							<%} %>
						</span>
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
                		var url = "TrainingClassFieldManage.do?classId=" + classId;
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