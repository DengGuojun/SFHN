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
	TrainingClassAcceptBean bean = (TrainingClassAcceptBean)request.getAttribute("ClassAccept");
	List<TrainingClassInfoBean> classInfoList = (List<TrainingClassInfoBean>)request.getAttribute("ClassInfoList");
	TrainingOrganizationInfoBean trainingOrgBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgBean");
	FileInfoBean acceptFormBean  = (FileInfoBean)request.getAttribute("AcceptFormBean");
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
        <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>项目验收</span>
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
                    <form class="form-box col-xs-8" id="formData" method="post" novalidate="novalidate" data-widget-cid="widget-1" onsubmit="javascript:return checkForm('formData');">
                        <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
   		                <input type="hidden" name="acceptStatus" value="<%=bean.getAcceptStatus() %>"/>
   		                <input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训机构</div>
                                <div class="col-xs-10">
                                    <span class="form-control"><%=trainingOrgBean.getOrganizationName() %></span>
                                </div>
                            </div>
                            <div class="form-group select-item">
                                <div class="col-xs-2 form-label">培育班</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="classId" checkStr="培训班;txt;true;;200" >
					                    <option></option>
					                    <%for(TrainingClassInfoBean classInfoBean : classInfoList){ %>
											<option value="<%=classInfoBean.getClassId()%>"  <%=(bean.getClassId() == classInfoBean.getClassId()) ? "selected" : "" %>><%=classInfoBean.getClassName() %></option>
										<%} %>
				                   </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">办公地点</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" placeholder="请输入" name="trainingAddress" id="trainingAddress" value="<%=bean.getTrainingAddress() %>" checkStr="办班地点;txt;true;;200">                                  
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">验收重点内容</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" placeholder="请输入" checkStr="验收重点;file;true;;200" name="acceptContent" id="acceptContent"  value="<%=bean.getAcceptContent() %>">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">项目验收表</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input type="file" id="file1" value="上传" class="form-control" onchange="up('file1',<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>);">
                                        </div>
                                        <input type="hidden" name="acceptFormFilePath" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>" value="<%=acceptFormBean !=null ? acceptFormBean.getFilePath(): "" %>" checkStr="项目验收表;txt;true;;1000"/>
				                        <input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>_id" value="<%=acceptFormBean !=null ? acceptFormBean.getFileId(): "" %>" />
                                    </div>
                                </div>
                                <p class="bg-info pd-15" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_ACCEPT_FORM %>_txt" style="display:none">已上传</p>
                            </div>
                            <div id="otherContent">
				           </div>
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
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
$(function(){
    mScroller();
})
$(document).ready(function() {
	 $(".select-item select").trigger('change');
	 $("#cancel").click(function() {
			if(confirm("确定要放弃保存吗?")){
				window.history.back();
			 }
		});
});
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