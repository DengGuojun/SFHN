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
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	String userName = (String)request.getAttribute("UserName");
	Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
	GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
	String organizationName = (String)request.getAttribute("OrganizationName");
	FileInfoBean fieldTrainingPhotoBean = (FileInfoBean)request.getAttribute("FieldTrainingPhotoBean");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>返回</span>
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
                <div class="main-bd pl-32">
                    <section class="section-wrp">
                        <h2 class="section-wrp-title"><%=classInfoBean.getClassName() %></h2>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培训机构：</div>
                                    <div class="col-xs-10"><%=organizationName %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培育年份：</div>
                                    <div class="col-xs-10"><%=classInfoBean.getTrainingYear() %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培育类型：</div>
                                    <div class="col-xs-10"><%=MapKit.getValueFromMap(classInfoBean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">开班人数：</div>
                                    <div class="col-xs-10"><%=classInfoBean.getClassPeopleQuantity() %></div>
                                </div>
                            </li>
                             <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">开班流程：</div>
                                    <div class="col-xs-10">
                                    <a onclick="javascript:location.href='TrainingClassInfoProcess.do?classId=<%=classInfoBean.getClassId()%>'" >查看详情</a>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">验收流程：</div>
                                    <div class="col-xs-10">
                                    <a onclick="javascript:location.href='TrainingClassAcceptProcess.do?classId=<%=classInfoBean.getClassId()%>'" >查看详情</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </section>
                    <section class="section-wrp">
                        <ul class="nav nav-tabs nav-justified">
                            <li><a href="TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>">学员信息</a></li>
                            <li ><a href="TrainingClassTeacherList.do?classId=<%=classInfoBean.getClassId()%>">教师信息</a></li>
                            <li><a href="TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>">集中培训材料</a></li>
                            <li class="active"><a href="TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>">田间实训材料</a></li>
                            <li><a href="TrackingServiceInfoList.do?classId=<%=classInfoBean.getClassId()%>">跟踪服务</a></li>
                             <%if(isGovernment){ %>
                            <li><a href="TrainingClassUserEvaluate.do?classId=<%=classInfoBean.getClassId()%>">学员评定</a></li>
                            <%} %>
                        </ul>
                        <div class="nav-content">
                            <div class="nav-item">
                                 <table class="table table-base table-odd">
                                <input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
                    		<tr class="bortop-no">
                    			<th>需要提交的文件</th>
                    			<th>状态</th>
                    			<th>时间</th>
                    			<th>操作</th>
                    		</tr>
                    		<tr>
                    			<td>田间实训计划</td>
                    			<%if(fieldTrainingPlan !=null){%>
                    			<td>已提交</td>
                    			<td><%=DateKit.formatTimestamp(fieldTrainingPlan.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fieldTrainingPlan.getFileId()%>'" class="">下载</a>
                    			<%if(!isGovernment) {%>
								<label class="ml-10 btn-upload upload-wrp"><a>上传</a><input type="file" id="file" multiple="multiple" onchange="up('file',<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN %>);"></label>
								<%} %>
                    			</td>
                    			<%} else{%>
                    			<td>未提交</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file" multiple="multiple" onchange="up('file',<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PLAN %>_id" value="<%=fieldTrainingPlan!=null ? fieldTrainingPlan.getFileId(): "" %>" />          
                    		</tr>
                    		<tr>
                    			<td>田间实训照片</td>
                    			<%if(fieldTrainingPhotoCount >0){%>
                    			<td>已提交</td>
                    			<td><%=DateKit.formatTimestamp(fieldTrainingPhotoBean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PHOTO%>'" class="">查看</a>
								</td>
                    			<%} else{%>
                    			<td>未提交</td>
                    			<td></td>
                    			<td>
                    			<%if(!isGovernment) {%>
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_FIELD_TRAINING_PHOTO%>'" class="">查看</a>
								<%} %></td>
                    			<%}%>                      			
                    		</tr>
                                </table>
                            </div>
                        </div>
                    </section>
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
mScroller();
function up(fileInput,fileType) {
	$('.loading-box').removeClass('dn');
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
                $('.loading-box').addClass('dn');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}
</script>
</html>