<%@page import="com.lpmas.sfhn.portal.config.TrainingClassUserConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	TrainingOrganizationInfoBean trainingOrganizationInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	TrainingClassUserBean userInfoBean = (TrainingClassUserBean)request.getAttribute("TrainingClassUserBean");
%>

<%@ include file="../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>课程</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="keywords" content="课程" />
    <meta name="description" content="课程" />
    <link type="image/x-icon" rel="shortcut icon" href="/favicon.ico"/>
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/yun/global.css">
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/yun/global-v2.css">
    <link rel="stylesheet" type="text/css" href="<%=STATIC_URL %>css/yun/course.css">
    <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
</head>
<body>
<div class="g-wrap g-pb0">
    <div class="g-h2 f-tac"><a onclick="history.back()" class="back-a"><i class="back-point"></i></a>培育班申报</div>
    <div class="placeHolder"></div>
    <div style="margin-bottom:60px">
        <div class="left-h2">培育班名称</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent"><%=classInfoBean.getClassName() %></article>
        </section>
        <div class="left-h2">培训机构</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
            <%=trainingOrganizationInfoBean.getOrganizationName() %>
            </article>
        </section>
        <div class="left-h2">培训类型</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=MapKit.getValueFromMap(classInfoBean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP)%>
            </article>
        </section>
        <div class="left-h2">招生人数</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=classInfoBean.getClassPeopleQuantity() %>人
            </article>
        </section>
        <div class="left-h2">培训时间</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=classInfoBean.getTrainingTime()%>
            </article>
        </section>
        <div class="left-h2">行程安排</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=classInfoBean.getTrainingSchedule()%>
            </article>
        </section>
        <div class="left-h2">报名截止</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=classInfoBean.getRegistrationEndTime()==null? "":DateKit.formatTimestamp(classInfoBean.getRegistrationEndTime(), DateKit.DEFAULT_DATE_FORMAT) %>
            </article>
        </section>
        <%if(userInfoBean!=null){ %>
        <div class="left-h2">审批结果</div>
        <section class="g-mb10 bg-df9">
            <article class="g-font-color course-detailContent">
                <%=MapKit.getValueFromMap(userInfoBean.getUserStatus(), TrainingClassUserConfig.USER_STATUS_MAP)%>
            </article>
        </section>
        <div class="placeHolder"></div>
        <%} %>
    </div>
</div>

	<%if(userInfoBean!=null&&!userInfoBean.getUserStatus().equals(TrainingClassUserConfig.USER_STATUS_NOT_APPROVE)){ %>
	<footer class="declare-btn bg-b7b">已报名</footer>
	<%}else{ %>
	<form id="formData" name="formData" method="post" action="TrainingClassManage.do">
		<input type="hidden" id="classId" name="classId" value="<%=classInfoBean.getClassId()%>">	
	</form>
	<footer class="declare-btn bg-b7b">未报名</footer>
	<%} %>

</body>
</html>