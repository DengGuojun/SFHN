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
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
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
                    <span class="u-title"><a href="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>'"><i class="icon-return"></i></a>上传文件</span>
                    <div class="dropdown fr">
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
                    <%if(!isGovernment) {%>
                    <div class="btn-upload-wrp">
                    	<div class="btn btn-primary btn-upload">上传文件<input type="file" id="file" multiple="multiple" onchange="up('file',<%=fileType%>);"></div>
                    </div>
                    <%} %>
                    <input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
                    <table class="table table-striped">
                    	<tbody class="files">
                    	<tr class="bortop-no">
                   		    <th>序号</th>
                   			<th>文件名</th>
                   			<th>更新时间</th>
                   			<th>操作</th>
                    	</tr>
                    	<%int count = 0;
                    	for(FileInfoBean bean : fileInfoList) {%>
                   		<tr class="template-upload fade in">   
                   		      <td><%=++count%></td>   
                   		      <%if(bean.getFileFormat().equals("jpg") || bean.getFileFormat().equals("jpeg") || bean.getFileFormat().equals("png")
                   		    		|| bean.getFileFormat().equals("JPG") || bean.getFileFormat().equals("JPEG") || bean.getFileFormat().equals("PNG")){ %>           		        
				              <td width="120px">
				              <span class="preview">
				              <div class="upload-img">
				              <img src="<%=PORTAL_URL %>/file/admin_file/<%=bean.getFilePath()%>"/> 
				              </div>
				              </span>
				              </td>
				              <%}else{%>
				              <td>
                   		          <p class="name"><%=bean.getFileName()%>.<%=bean.getFileFormat()%></p>
                   		       </td>	
                   		       <%} %>
                   		       <td><%=bean.getModifyTime() == null ? DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(bean.getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>	
                   		       <td>
                   		       <a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=bean.getFileId()%>'" class="">下载</a> 
                    			<%if(!isGovernment) {%>   
                    			 |<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=bean.getFileId()%>&url=TrainingClassImageList'" class="">删除</a>|    	       
								<label class="ml-10 btn-upload upload-wrp" for="file_<%=bean.getFileId()%>"><a>更换</a><input type="file" id="file_<%=bean.getFileId()%>" onchange="change('file_<%=bean.getFileId()%>',<%=bean.getFileId()%>,<%=fileType%>);"></label>															
								<%} %>
                    			               			
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

	   <div class="main-bd">
          <div class="loading-mask loading-box dn"><img src="<%=STATIC_URL %>images/loading.gif"></div>
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
    var file_data = $('#'+fileInput)[0].files; // for multiple files
    var classId = $("#classId").val();
    var infoType = '<%=InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO%>';
    for (var k = 0; k < file_data.length; k++) {
    	var subfix = file_data[k].name.substring(file_data[k].name.lastIndexOf(".")+1).toLowerCase();
    	var allowFileType = "<%=FileInfoConfig.ALLOWED_FILE_TYPE_MAP.get(fileType) %>"
    	if(allowFileType.indexOf(subfix) == -1){
    		alert("文件类型不符");
    		return;
    	}
    }
    $('.loading-box').removeClass('dn');
    var count =0;
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
                	flag = 'false';
                	alert(data.message);               	
                }
                ++count;
                $('.loading-box').addClass('dn');
                if(count == file_data.length){
                	alert("上传成功");
               		var url = "TrainingClassImageList.do?classId=" + classId +"&fileType="+fileType;
               		window.location.href= url
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}
function change(fileInput,existId,fileType) {
	$('.loading-box').removeClass('dn');
    var file_data = $('#'+fileInput)[0].files; // for multiple files
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
                		var url = "TrainingClassImageList.do?classId=" + classId + "&fileType="+fileType;
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