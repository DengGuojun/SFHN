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
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	List<TrainingClassUserBean> list = (List<TrainingClassUserBean>)request.getAttribute("ClassUserList");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	Map<Integer, DeclareReportBean> declareReportMap = (Map<Integer, DeclareReportBean>)request.getAttribute("DeclareReportMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	Map<Integer, String> industryTypeMap = (Map<Integer, String>)request.getAttribute("IndustryTypeMap");
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
	<title>培训班详情</title>
	<link rel="stylesheet" href="<%=STATIC_URL%>css/class.css">
	<script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/hotcss.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
</head>
<body class="gray-bg">
	<div class="container">
		<div class="container-bd mt0">
			<div class="g-nav mb18">
				<div class="nav-item w-5-1 fl selected" ><span>学员名单</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassPlanManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>年度计划</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>集中培训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>'"><span>田间实训</span></div>
				<div class="nav-item w-5-1 fl" onclick="javascript:location.href='ExpertInfoList.do?classId=<%=classInfoBean.getClassId()%>'"><span>跟踪服务</span></div>
			</div>
			<%for(TrainingClassUserBean bean : list) {
				DeclareReportBean reportBean = declareReportMap.get(bean.getDeclareId());
			%>
			<section class="panel-wrp">
				<div class="panel-item panel-extra">
					<div class="panel-detail">
						<div class="panel-h3">
							<span><%=reportBean.getUserName() %></span>
							<span class="fr"><%=MapKit.getValueFromMap(reportBean.getUserGender(), GenderConfig.GENDER_MAP) %></span>
						</div>
						<div class="panel-tips">
						<span><%=reportBean.getIdentityNumber() %></span>
						<span class="fr"><%=MapKit.getValueFromMap(reportBean.getIndustryTypeId1(),industryTypeMap) %></span>
						</div>
						<i class="icon-arrow"  onclick="javascript:location.href='TrainingClassUserManage.do?userId=<%=reportBean.getUserId() %>&identityNumber=<%=reportBean.getIdentityNumber() %>'"></i>
					</div>
				</div>
			</section>
			<%} %>
		</div>
		
		<!-- 分页栏 -->
	<form name="formPage" method="post" action="">
		<input type="hidden" name="pageNum" id="pageNum" value=""/>
		<%for(String[] condArray :COND_LIST){ %>
		<input type="hidden" name="<%=condArray[0] %>" id="<%=condArray[0] %>" value="<%=condArray[1] %>"/><%} %>
	</form>
	<div class="page-num clearfix">
		<%if(PAGE_BEAN.getCurrentPageNumber()>1){ %>
		<div class="btn-page">
			<button class="u-btn btn-prev" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getPrePageNumber() %>);">上一页</button>
		</div>
		<%} %>
		<%if(PAGE_BEAN.getTotalPageNumber() >1){ %>
		<div class="select-box">
			<span class="page-txt"><%=PAGE_BEAN.getCurrentPageNumber() %> / <%=PAGE_BEAN.getTotalPageNumber() %></span>
			<select id="pageBar" onchange="jump()">
				<%for(int i=1; i<=PAGE_BEAN.getTotalPageNumber() ; i++) {%>
				<option value="<%=i%>" <%=PAGE_BEAN.getCurrentPageNumber() == i ? "selected" : "" %> >第<%=i%>页</option>
				<%} %>
			</select>
		</div>
		<%} %>
		<%if(PAGE_BEAN.getCurrentPageNumber() < PAGE_BEAN.getTotalPageNumber()){ %>
		<div class="btn-page">
			<button class="u-btn btn-next" onclick="javascript:goPage('formPage',<%=PAGE_BEAN.getNextPageNumber() %>);">下一页</button>
		</div>
		<%} %>
	</div>
	</div>
</body>
<script>
function jump(){
	var page = $("#pageBar  option:selected").val();
	goPage('formPage',page);
}
</script>
</html>