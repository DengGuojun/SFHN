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
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%
	AnnouncementInfoBean bean = (AnnouncementInfoBean)request.getAttribute("AnnouncementInfo");
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
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
	<title>公告详情</title>
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg pg-bottom">
	<div class="container mt0">
		<form id="formData" class="form-horizontal" method="post" novalidate="novalidate" data-widget-cid="widget-1" onsubmit="javascript:return checkForm('formData');">
	    <input type="hidden" name="organizationType" value="<%=bean.getOrganizationType() %>"/>
	    <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
	    <input type="hidden" id="announcementId" name="announcementId" value="<%=bean.getAnnouncementId() %>"/>
   		<input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
		<div class="container-bd">
			<div class="notice-box">
				<div class="notice-hd mb18"><input type="text" placeholder="请输入标题" name="announcementTitle" value="<%=bean.getAnnouncementTitle()%>" checkStr="标题;txt;true;;200"></div>
				<div class="notice-bd">
					<textarea class="pd-text" name="announcementContent" placeholder="请输入公告内容" checkStr="公告内容;txt;true;;100000"><%=bean.getAnnouncementContent()%></textarea>
				</div>
			</div>
			<div class="main-section">
				<div class="main-hd pd43 little">
					<h4>接收者</h4>
				</div>
				<div class="main-bd detail-box notice-extra js-detail">
					<ul>
						<li class="selected">
							<div class="detail-cont"><div class="cont-title">省级主管部门</div></div>
							<input type="hidden" name="noticeProvince" value="<%=Constants.STATUS_VALID%>"/>
						</li>
						<li class="selected">
							<div class="detail-cont"><div class="cont-title">市级主管部门</div></div>
							<input type="hidden" name="noticeCity" value="<%=Constants.STATUS_VALID%>"/>
						</li>
						<li class="selected">
							<div class="detail-cont"><div class="cont-title">县级主管部门</div></div>
							<input type="hidden" name="noticeRegion" value="<%=Constants.STATUS_VALID%>"/>
						</li>
						<li class="selected">
							<div class="detail-cont"><div class="cont-title">培训机构</div></div>
							<input type="hidden" name="noticeTrainingOrg" value="<%=Constants.STATUS_VALID%>"/>
						</li>
					</ul>
				</div>
			</div>
			<div class="main-section">
				<div class="main-hd pd43 little">
					<h4>附件</h4>
				</div>
				<dl class="info-list info-done">
				<dd class="list-item" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>_txt" style="display:none"><span class="list-title">附件</span><span class="fr list-tap-done">已上传</span></dd>
				</dl>
				<div class="main-bd detail-box noctice-extra">
					<a class="btn w-1-1 btn-upfile int-upfile"><input type="file" id="file" class="up-file" onchange="up('file',<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>);"><button>上传附件</button></a>
					<input type="hidden" name="announcementAttachFilePath" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT).getFileType(): "" %>" checkStr="公告附件;file;false;;1000"/>
					<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT).getFileId(): "" %>" />
				</div>
			</div>
		</div>
		<div class="fixed-bottom pd-btn-wrp fixed-width">
			<div class="btn btn-upload w-1-1">
			<%if(bean.getAnnouncementId()>0) {%>
			<button>修改公告</button>
			<%}else{ %>
			<button>发公告</button>
			<%} %>
			</div>
		</div>
		</form>
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
    var announcementId = $("#announcementId").val();
    var infoType = '<%=InfoTypeConfig.INFO_TYPE_ANNOUNCEMENT%>';
    var fileType = '<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT%>';
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
        		url: 'FileInfoUpload.do?fileId='+existId+'&infoId1='+announcementId+'&infoType='+infoType+"&fileType="+fileType,
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
$(function(){
	$('.js-detail li').click(function(){
		if($(this).hasClass('selected')){
			$(this).removeClass('selected');
			$(this).find("input").val(0)
		}else{
			$(this).addClass('selected');
			$(this).find("input").val(1)
		}
	});
})
</script>
</html>