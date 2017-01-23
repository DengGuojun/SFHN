<%@page import="com.lpmas.sfhn.portal.config.TrainingClassInfoConfig"%>
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
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.TrainingClassStatusHelper"  %>
<%@ page import="com.lpmas.sfhn.business.TrainingOrganizationInfoHelper"  %>
<%
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	List<FileInfoBean> fileInfoList  = (List<FileInfoBean>)request.getAttribute("FileInfoList");
	int fileType = (Integer)request.getAttribute("FileType");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>上传文件</span>
                    <div class="dropdown fr">
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
                    <%if(!isGovernment) {%>
                    <div class="btn-upload-wrp">
                    	<div class="btn btn-primary btn-upload">上传文件<input type="file" id="file" multiple="multiple" accept="image/*" onchange="up('file',<%=fileType%>);"></div>
                    </div>
                    <%} %>
                    <input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
                    <table class="table table-striped">
                    	<tbody class="files">
                    	<%for(FileInfoBean bean : fileInfoList) {%>
                    		<tr class="template-upload fade in">                 		        
					              <td width="120px">
					              <span class="preview">
					              <div class="upload-img">
					              <img src="<%=PORTAL_URL %>/file/admin_file/<%=bean.getFilePath()%>"/> 
					              </div>
					              </span>
					              </td>
					              <td>
                    		            <p class="name"><%=bean.getFileName() %></p>
                    		       </td>				                
                    		    </tr>
                    	<%} %>
                    	</tbody>
                    </table>
                    <!-- 分页栏 -->
	               <%@ include file="../../../include/web/page.jsp"%>
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
<script src="<%=STATIC_URL %>js/jquery.ui.widget.js"></script>
<script src="<%=STATIC_URL %>js/jquery.iframe-transport.js"></script>
<script src="<%=STATIC_URL %>js/jquery.fileupload.js"></script>
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%=STATIC_URL %>js/jquery.xdr-transport.js"></script>
<![endif]-->
<script type="text/javascript">
$(function(){
    mScroller();
})
function up(fileInput,fileType) {
	$(".loading-box").show();
    var file_data = $('#'+fileInput)[0].files; // for multiple files
    var classId = $("#classId").val();
    var infoType = '<%=InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO%>';
    var flag = 'true';
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
        		url: 'FileInfoUpload.do?infoId1='+classId+'&infoType='+infoType+"&fileType="+fileType,
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.code != '1'){
                	alert(data.message);
                	flag = 'false';
                }
                $(".loading-box").hide();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
    if(flag == 'true'){
    alert("上传成功");
	var url = "TrainingClassImageList.do?classId=" + classId +"&fileType="+fileType;
	window.location.href= url
    }
}
</script>
</html>