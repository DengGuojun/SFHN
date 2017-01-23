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
	TrainingClassAcceptBean bean = (TrainingClassAcceptBean)request.getAttribute("ClassAccept");
	List<TrainingClassInfoBean> classInfoList = (List<TrainingClassInfoBean>)request.getAttribute("ClassInfoList");
	TrainingOrganizationInfoBean trainingOrgBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgBean");
	FileInfoBean acceptFormBean  = (FileInfoBean)request.getAttribute("AcceptFormBean");
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
	<title>项目验收</title>
	<link rel="stylesheet" href="<%=STATIC_URL %>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/zepto/1.1.4/zepto.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg pg-bottom">
	<div class="container">
		<form id="formData" class="form-horizontal" method="post" novalidate="novalidate" data-widget-cid="widget-1" onsubmit="javascript:return checkForm('formData');">
	    <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
   		<input type="hidden" name="acceptStatus" value="<%=bean.getAcceptStatus() %>"/>
   		<input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
		<div class="container-bd mt0">
			<div class="main-section">
				<div class="main-bd mb18">
					<ul>
						<li><label class="u-title"><span>培训机构：</span></label><div class="u-content"><span><%=trainingOrgBean.getOrganizationName() %></span></div></li>
						<li><label class="u-title"><span>培训班：</span></label><div class="u-content select-box">
							<div class="select-item">
								<span class="select-txt">请选择</span>
								<select class="form-select" name="classId" checkStr="培训班;txt;true;;200" >
					                    <option></option>
					                    <%for(TrainingClassInfoBean classInfoBean : classInfoList){ %>
											<option value="<%=classInfoBean.getClassId()%>"  <%=(bean.getClassId() == classInfoBean.getClassId()) ? "selected" : "" %>><%=classInfoBean.getClassName() %></option>
										<%} %>
				                </select>
							</div>
						</div></li>
						<li>
							<label class="u-title"><span>办班地点：</span></label>
							<div class="u-content">
								<div class="input-item">
									<input type="text" placeholder="请输入" name="trainingAddress" id="trainingAddress" value="<%=bean.getTrainingAddress() %>" checkStr="办班地点;txt;true;;200"/>
								</div>
							</div>
						</li>
						<li>
							<label class="u-title "><span>验收重点内容：</span></label>
							<div class="u-content">
								<textarea class="pd-text" style="height: 6rem; border: 0; padding: 0;" placeholder="请输入" checkStr="验收重点;file;true;;200" name="acceptContent" id="acceptContent"><%=bean.getAcceptContent() %></textarea>
							</div>
						</li>
					</ul>
				</div>
				<dl class="info-list info-done">
					<dd class="list-item" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>_txt" style="display:none"><span class="list-title">项目验收表</span><span class="fr list-tap-done">已上传</span></dd>
				</dl>
				<div class="main-bd" >
				<a class="btn w-1-1 btn-upfile int-upfile">
					<input type="file" class="up-file"  id="file1" class="up-file" onchange="up('file1',<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>);"><button>上传项目验收表</button>
				</a>
				<input type="hidden" name="acceptFormFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>" value="<%=acceptFormBean !=null ? acceptFormBean.getFilePath(): "" %>" checkStr="项目验收表;txt;true;;1000"/>
				<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>_id" value="<%=acceptFormBean !=null ? acceptFormBean.getFileId(): "" %>" />
				</div>
				<div id="otherContent">
				</div>
			</div>
			<div class="fixed-bottom pd-btn-wrp fixed-width">
				<a class="btn w-1-1 btn-submit">
				<%if(bean.getClassId() > 0){ %>
				<button>编辑项目验收</button>
				<%}else{ %>
				<button>新建项目验收</button>
				<%} %>
				</a>
			</div>
		</div>
	</form>
	<div class="loading-box" style="display: none; ">
			<img src="<%=STATIC_URL %>images/loading-icon.png" class="loading-icon rotate">
	</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	 $(".select-item select").trigger('change');
});
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
	var classId = $(this).children('option:selected').val();
	$.ajax({
        url: 'TrainingClassAcceptMaterialList.do?classId='+classId,
        type: 'GET',
        success: function (data) {
        		$("#otherContent").html(data)
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
	
});

</script>
</html>