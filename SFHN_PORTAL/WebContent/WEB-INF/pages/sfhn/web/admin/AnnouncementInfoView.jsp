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
	AnnouncementInfoBean bean = (AnnouncementInfoBean)request.getAttribute("AnnouncementInfo");
	FileInfoBean announcementAttach  = (FileInfoBean)request.getAttribute("AnnouncmentAttatch");
	boolean isOwner = (Boolean)request.getAttribute("isOwner");
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
                    <span class="u-title"><a href="javascript:" onclick="self.location=document.referrer;"><i class="icon-return"></i></a>公告详情</span>
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
                    <article class="notice-box">
                        <input type="hidden" id="announcementId" value="<%=bean.getAnnouncementId() %>"/>
                        <%if(bean.getStatus() == Constants.STATUS_VALID) {%>
                        <h1 class="notice-title"><%=bean.getAnnouncementTitle() %></h1>
                    	<em class="notice-date"><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT) %></em>
                    	<p id="announcementContent">
                    		 <%=bean.getAnnouncementContent() %>
                    	</p>
                        <%} else{%>
				        <h1 class="notice-title">该公告已删除</h1>
				        <%} %>
                    </article>
                    <%if(announcementAttach != null) {%>
                    <section class="section-wrp default">
                        <h2 class="section-wrp-title">附件</h2>
                       	<p class="global-tips"><%=announcementAttach.getFileName() %>.<%=announcementAttach.getFileFormat() %><a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=announcementAttach.getFileId()%>'" class="fr">下载</a></p>
                    </section>
                    <%} %>
                    <%if(isOwner) {%>
                    <div class="text-center btn-wrp">
			            <button type="button" class="btn btn-mini btn-default js-reject" id="delete" >删除</button>
					    <button type="button" class="btn btn-mini btn-success js-pass" onclick="javascript:location.href='AnnouncementInfoManage.do?announcementId=<%=bean.getAnnouncementId()%>'">编辑</button>
			        </div>
			       <%} %>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script>
$(function(){
    mScroller();
})
$(document).ready(function() {
	$('#announcementContent').html($('#announcementContent').html().replace(/\n/g,'<br/>'));
	$("#delete").click(function() {
		if(confirm("确定要删除吗?")){
			var id = $("#announcementId").val();
			var url = "AnnouncementInfoRemove.do?announcementId="+ id ;
			window.location.href= url
		 }
	});
});
</script>
</html>