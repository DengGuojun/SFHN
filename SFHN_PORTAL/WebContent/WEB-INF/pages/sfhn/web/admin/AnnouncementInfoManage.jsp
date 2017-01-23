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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>新公告</span>
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
                    <form class="form-box col-xs-8" id="formData" method="post" novalidate="novalidate" data-widget-cid="widget-1" onsubmit="javascript:return checkForm('formData');">
                        <input type="hidden" name="organizationType" value="<%=bean.getOrganizationType() %>"/>
	                    <input type="hidden" name="organizationId" value="<%=bean.getOrganizationId() %>"/>
	                    <input type="hidden" id="announcementId" name="announcementId" value="<%=bean.getAnnouncementId() %>"/>
   		                <input type="hidden" name="status" value="<%=bean.getStatus() %>"/>
                        <section class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">标题</div>
                                <div class="col-xs-10">
                                <input type="text" class="form-control" placeholder="请输入标题" name="announcementTitle" value="<%=bean.getAnnouncementTitle()%>" checkStr="标题;txt;true;;200">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">内容</div>
                                <div class="col-xs-10">
                                    
                                    <textarea class="form-control" rows="10" name="announcementContent" placeholder="请输入公告内容" checkStr="公告内容;txt;true;;100000"><%=bean.getAnnouncementContent()%></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">接收者</div>
                                <div class="col-xs-10 js-detail">
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox1" value="<%=Constants.STATUS_NOT_VALID %>"> 省级主管部门
                                      <input type="hidden" name="noticeProvince" value="<%=Constants.STATUS_NOT_VALID%>"/>
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox2" value="<%=Constants.STATUS_NOT_VALID%>"> 市级主管部门
                                      <input type="hidden" name="noticeCity" value="<%=Constants.STATUS_NOT_VALID%>"/>
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox3" value="<%=Constants.STATUS_NOT_VALID%>"> 县级主管部门
                                      <input type="hidden" name="noticeRegion" value="<%=Constants.STATUS_NOT_VALID%>"/>
                                    </label>
                                    <label class="checkbox-inline">
                                      <input type="checkbox" id="inlineCheckbox4" value="<%=Constants.STATUS_NOT_VALID%>"> 培训机构
                                      <input type="hidden" name="noticeTrainingOrg" value="<%=Constants.STATUS_NOT_VALID%>"/>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">附件</div>
                                <div class="col-xs-10"><input type="file" id="file" class="form-control" onchange="up('file',<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>);"></div>
                                <input type="hidden" name="announcementAttachFilePath" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT).getFileType(): "" %>" checkStr="公告附件;file;false;;1000"/>
					            <input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT) !=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT).getFileId(): "" %>" />
					            <p class="bg-info pd-15" id="<%=FileInfoConfig.FILE_TYPE_ANNOUNCEMENT_ATTACHMENT %>_txt" style="display:none">文件上传成功</p>
                            </div>
                        </section>
                        <div class="btn-wrp text-center">
                            <button class="btn first btn-success btn-mini">发布</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
		<div class="loading-box" style="display: none; ">
			<img src="<%=STATIC_URL %>images/loading-icon.png" class="loading-icon rotate">
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
	$('.js-detail label').click(function(){
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