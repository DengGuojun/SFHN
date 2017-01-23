<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%
	DeclareInfoBean declareInfoBean = (DeclareInfoBean)request.getAttribute("DeclareInfoBean");
	FarmerContactInfoBean contactInfoBean = (FarmerContactInfoBean)request.getAttribute("ContactInfoBean");
	List<TrainingClassInfoBean> list = (List<TrainingClassInfoBean>)request.getAttribute("TrainingClassInfoList");
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
</head>
<body>
<div class="g-wrap g-pb0">
    <div class="g-h2 f-tac"><a class="back-a"><i class="back-point"></i></a>选择近期培育班报名</div>
    <div class="placeHolder"></div>
    <div class="g-wrap g-pb0">
        <div class="classes-list last-bor">
        <%for(TrainingClassInfoBean bean:list){ %>
			<div class="courseDetail">
                <a href="TrainingClassManage.do?classId=<%=bean.getClassId()%>" class="courseList1">
                    <div class="list-right noneImg">
                        <p class="s-course-name overline2"><%=bean.getClassName() %></p>
                        <p class="s-course-progress"><span><%=bean.getRegion() %></span></p>
                    </div>
                </a>
            </div>
		<%} %>
        </div>
    </div>
</div>
</body>
<script>
document.querySelector('.back-a').addEventListener('click',function(event){
    event.preventDefault();
    postAction({"action":"close"});
    window.location.href = this.getAttribute('href');
});
function postAction(message) {
    var u = navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    if (isiOS) {
        window.webkit.messageHandlers.WebViewJavascriptBridge.postMessage(message)
    } else {
    }
}
</script>
</html>