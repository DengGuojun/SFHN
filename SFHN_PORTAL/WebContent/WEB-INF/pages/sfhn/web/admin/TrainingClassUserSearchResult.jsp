<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
String identityNumber = (String)request.getAttribute("identityNumber");
String userName = (String)request.getAttribute("userName");
String userGender = (String)request.getAttribute("userGender");
String education = (String)request.getAttribute("education");
String industryScale = (String)request.getAttribute("industryScale");
String industryType = (String)request.getAttribute("industryType");
String message = (String)request.getAttribute("message");
boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
String orgUserName = (String)request.getAttribute("UserName");
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
                    <span class="u-title">招生筛选查询</span>
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
                    <form class="form-box col-xs-8" method="post" action="TrainingClassUserSearch.do" id="TrainingClassUserSearch">
                        <section class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">身份证号</div>
                                <div class="col-xs-10"><input type="text" id="identityNumber" name="identityNumber" placeholder="请输入身份证号" class="form-control" value="<%=identityNumber %>"/></div>
                            </div>
                        </section>
                        <div class="btn-wrp text-center">
                            <button type="button" class="btn first btn-success btn-mini" onclick="submitSearch();">查询</button>
                        </div>
                    </form>
                    <hr>
                    <div class="mg-auto col-xs-8 js-result">
                    <%if(!userName.isEmpty()){ %>
                    	<p class="pd-15 bg-danger"><%=message %></p> <!-- 未参加用 bg-info -->
                    	<ul class="list-group">
                    		<li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">姓名：</div>
                    				<div class="col-xs-10"><%=userName %></div>
                    			</div>
                    		</li>
                    		<li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">身份证号：</div>
                    				<div class="col-xs-10"><%=identityNumber %></div>
                    			</div>
                    		</li>
                    		<!-- <li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">培训时间：</div>
                    				<div class="col-xs-10"></div>
                    			</div>
                    		</li> -->
                    		<li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">生产类型：</div>
                    				<div class="col-xs-10"><%=industryType %></div>
                    			</div>
                    		</li>
                    		<!-- <li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">年龄：</div>
                    				<div class="col-xs-10"></div>
                    			</div>
                    		</li> -->
                    		<li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">学历：</div>
                    				<div class="col-xs-10"><%=education %></div>
                    			</div>
                    		</li>
                    		<!-- <li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">作物：</div>
                    				<div class="col-xs-10"></div>
                    			</div>
                    		</li> -->
                    		<li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">面积：</div>
                    				<div class="col-xs-10"><%=industryScale %></div>
                    			</div>
                    		</li>
                    		<!-- <li class="list-group-item">
                    			<div class="row">
                    				<div class="col-xs-2">专业：</div>
                    				<div class="col-xs-10"></div>
                    			</div>
                    		</li> -->
                    	</ul>
                    	<%}else{%>
                    	<p class="pd-15 bg-info"><%=message %></p>
                    	<%} %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
mScroller();
function submitSearch() {
	$('.js-result').addClass('dn');
	var identityNumber = $("#identityNumber").val();
	var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if (identityNumber == ''){
		alert("请输入身份证号");
		return;
	}
	if(reg.test(identityNumber) === false)
	{
	    alert("身份证输入不合法");
	    return ;
	}
     document.getElementById("TrainingClassUserSearch").submit();
       
 }

</script>
</html>