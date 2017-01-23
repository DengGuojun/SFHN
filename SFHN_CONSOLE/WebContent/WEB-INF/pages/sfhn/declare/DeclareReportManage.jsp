<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*"%>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.admin.bean.*"  %>
<%@ page import="com.lpmas.admin.business.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<% 
    DeclareReportBean bean = (DeclareReportBean)request.getAttribute("DeclareReportBean");
	AdminUserHelper adminUserHelper = (AdminUserHelper)request.getAttribute("AdminUserHelper");
	int declareId = ParamKit.getIntParameter(request, "declareId", 0);
	String jobType = ParamKit.getAttribute(request, "JobType");
	String jobName = ParamKit.getAttribute(request, "JobName");
	String industryJobType = ParamKit.getAttribute(request, "IndustryJobType");
	String industryJobName = ParamKit.getAttribute(request, "IndustryJobName");
	Map<Integer, NationalCertificationBean> nationalCertificationMap = (Map<Integer, NationalCertificationBean>) request.getAttribute("nationalCertificationMap");
	Map<Integer, IndustryTypeBean> industryTypeMap = (Map<Integer, IndustryTypeBean>) request.getAttribute("industryTypeMap");
	Map<Integer, IndustryInfoBean> industryInfoMap = (Map<Integer, IndustryInfoBean>) request.getAttribute("industryInfoMap");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="../../include/header.jsp" %>  
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>职业农民申请信息管理</title>
	<link href="<%=STATIC_URL %>/css/main.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="../js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>/js/common.js"></script>
</head>
<body class="article_bg">
	<div class="article_tit">
		<a href="javascript:history.back()" ><img src="<%=STATIC_URL %>/images/back_forward.jpg"/></a> 
		<ul class="art_nav">
			<li><a href="DeclareReportList.do">职业农民申请信息列表</a>&nbsp;>&nbsp;</li>			
			<li><%=bean.getUserName() %>&nbsp;>&nbsp;</li>
			<li><%=DeclareInfoConfig.DECLARE_TYPE_MAP.get(bean.getDeclareType())%>申报表</li>			
		</ul>
	</div>
          <p>基本信息</p>
		<div class="modify_form">
			<p>
				<em class="int_label">申报ID：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getDeclareId() %>" />
			</p>
			<p>
				<em class="int_label">姓名：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserName() %>" />
			</p>
			<p>
				<em class="int_label">性别：</em>
				<%if(bean.getUserGender() == 1){ %>
				<em>男</em>
				<%}else{ %>
				<em>女</em>
				<%} %>
			</p>
			<p>
				<em class="int_label">图片：</em>
				<img src="<%=bean.getImagePath()%>" width="50" height="75"/>
			</p>
			<p>
				<em class="int_label">出生日期：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserBirthday() == null ? "" : bean.getUserBirthday()%>" />
			</p>
			<p>
				<em class="int_label">民族：</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getNationality(), NationalityConfig.NATIONALITY_MAP) %>" />
			</p>
			<p>
				<em class="int_label">文化程度：</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getEducation(), FarmerInfoConfig.EDUCATION_LEVEL_MAP) %>" />
			</p>
			<p>
				<em class="int_label">身份证号：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIdentityNumber() %>" />
			</p>
			<p>
				<em class="int_label">政治面貌：</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getPoliticalStatus(), FarmerInfoConfig.POLITICAL_STATUS_MAP) %>" />
			</p>					
			<p>
				<em class="int_label">申报时间：</em>
				<input size="50" disabled="disabled" type="text" value="<%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%>" />
			</p>
		</div>
		<p>联系信息</p>
		<div class="modify_form">
			<p>
				<em class="int_label">手机号码：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserMobile() %>" />
			</p>
			<p>
				<em class="int_label">电子邮箱：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserEmail()%>" />
			</p>
			<p>
				<em class="int_label">QQ号：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserQq()%>" />
			</p>
			<p>
				<em class="int_label">微信号：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getUserWechat()%>" />
			</p>
			<p>
				<em class="int_label">户籍所在地：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getProvince()%><%=bean.getCity()%><%=bean.getRegion()%>" />
			</p>
			<p>
				<em class="int_label">通讯地址：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getAddress()%>" />
			</p>		
		</div>
        <p>申请培训信息</p>
		<div class="modify_form">
		    <p>
				<em class="int_label">申请方式:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getApplyType(), DeclareInfoConfig.APPLY_TYPE_MAP)%>" />
			</p>
			<p>
				<em class="int_label">是否参加过新型职业农民培训：</em>
				<%if(bean.getIsTrained() == 1){ %>
				<em>是</em>
				<%}else{ %>
				<em>否</em>
				<%} %>
			</p>
			<p>
				<em class="int_label">参加其它农业培训：( 次／年)</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getOtherTrainingTime()%>" />
			</p>
			<%if(bean.getHasNewTypeCertification() == 1) {%>
			<p>获得新型职业农民资格证</p>
			<p>
				<em class="int_label">认定等级:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getCertificationGrade(),FarmerInfoConfig.CERTIFICATION_LEVEL_MAP) %>" />
			</p>
			<p>
				<em class="int_label">认定时间:</em>
				<input size="50" disabled="disabled" type="text" value="<%=DateKit.formatTimestamp(bean.getCertificationDate(), DateKit.DEFAULT_DATE_TIME_FORMAT) %>" />
			</p>
			<p>
				<em class="int_label">认定部门:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getCertificationDepartment() %>" />
			</p>
			<%}else{%>
			<p>未获得新型职业农民资格证</p>
			<%}%>
			<p>
				<em class="int_label">农民技术等级或职称证书 ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getCertificationTitle(), FarmerInfoConfig.FARMER_TITLE_MAP)%>" />
			</p>
			<%if(bean.getHasNoCertification() == 1) {%>
			<p>未获得证书</p>
			<%}else{ %>
			<%if(bean.getHasNewTypeTrainingCertification() == 1) %>
			<p>获得新型职业农民培训证书</p>
			<%if(bean.getHasNationalCertification() == 1) %>
			<p>获得国家职业资格证书</p>
			<%} %>			
		</div>
		<%if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER || bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER) {%>
		<p>农务工作信息</p>
		<div class="modify_form">
			<p>
				<em class="int_label">从业工种:</em>
				<input size="50" disabled="disabled" type="text" value="<%=jobType %>" />
			</p>
			<p>
				<em class="int_label">从事岗位:</em>
				<input size="50" disabled="disabled" type="text" value="<%=jobName%>" />
			</p>
			<p>
				<em class="int_label">从业年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience()%>" />
			</p>
			<p>
				<em class="int_label">个人从事该工种/岗位年收入（万）:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIncome()%>" />
			</p>
			<p>
				<em class="int_label">从业单位类别:</em>
				<input size="50" disabled="disabled" type="text" value="<%=FarmerJobInfoConfig.JOB_COMPANY_TYPE_MAP.get(bean.getCompanyType()) %>" />
			</p>
			<p>
				<em class="int_label">工作地点 ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getJobProvince()%><%=bean.getJobCity()%><%=bean.getJobRegion()%>" />
			</p>		
		</div>
		<%} else {%>
		<p>生产经营状况</p>
		<div class="modify_form">
		<%if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER) {%>
		    <p>
				<em class="int_label">人员类别：</em>
				<input size="50" disabled="disabled" type="text" value="<%=FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %>" />
			</p>
		<%}%>
		<%if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER) {%>
		    <p>
				<em class="int_label">人员类别：</em>
				<input size="50" disabled="disabled" type="text" value="<%=FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %>" />
			</p>
		<%}%>
		<%if (bean.getDeclareType() == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) {%>
		    <p>
				<em class="int_label">人员类别：</em>
				<input size="50" disabled="disabled" type="text" value="<%=FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_MAP.get(bean.getFarmerType()) %>" />
			</p>
		<%}%>
		<%if (bean.getFarmerType() == FarmerInfoConfig.PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER) {%>
		    <p>
				<em class="int_label">工作地点 ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>" />
			</p>
			<p>
				<em class="int_label">从业工种:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryJobType %>" />
			</p>
			<p>
				<em class="int_label">从事岗位:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryJobName %>" />
			</p>
			<p>
				<em class="int_label">服务规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getServiceScale() %>" />
			</p>
			<p>
				<em class="int_label">从业年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryExperience()%>" />
			</p>
			<p>
				<em class="int_label">个人从事该工种/岗位年收入（万）:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryIncome()%>" />
			</p>
		<%}%>
		<%if (bean.getFarmerType() == FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER) {%>
		   <%if(bean.getHasRegisted() == 1) {%>
			<p>已登记注册家庭农场情况</p>
			<%}else{ %>
			<p>未登记注册家庭农场情况</p>
			<%}%>
			<%if(bean.getIsExampleFamilyFarm() == 1) {%>
			<p>是示范性家庭农场</p>
			<p>
				<em class="int_label">示范性家庭农场级别:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getExampleFarmLevel(), FarmerIndustryInfoConfig.FARM_LEVEL_MAP) %>" />
			</p>
			<%}else{ %>
			<p>不是示范性家庭农场</p>
			<%}%>
			<p>
				<em class="int_label">家庭农场类型:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getFamilyFarmType(),FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP) %>" />
			</p>
			<p>
				<em class="int_label">土地经营规模(亩) ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getFarmLandScale()%>" />
			</p>
			<p>
				<em class="int_label">家庭人口：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getFamilyPerson()%>" />
			</p>
			<p>
				<em class="int_label">家庭从事产业人数:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getFamilyWorkingPerson() %>" />
			</p>
			<p>
				<em class="int_label">上年度家庭收入（万元）:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getLastYearFamilyIncome() %>" />
			</p>
			 <p>
				<em class="int_label">产业所在地 ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>" />
			</p>
			
			<p>
				<em class="int_label">地区类型:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getAreaType(), FarmerIndustryInfoConfig.AREA_TYPE_MAP)%>" />
			</p>
			<p>
				<em class="int_label">经济区域类型:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getEconomicAreaType(), FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_MAP)%>" />
			</p>
			<p>主体产业</p>
			<%if(bean.getIndustryTypeId1() != 0){ %>
			<p>
				<em class="int_label">主体产业1:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId1()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称1:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId1()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale1() %>" /><%=bean.getIndustryUnit1()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience1() %>" />
			</p>
			<% }%>
			<%if(bean.getIndustryTypeId2() != 0){ %>
			<p>
				<em class="int_label">主体产业2:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId2()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称2:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId2()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale2() %>" /><%=bean.getIndustryUnit2()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience2() %>" />
			</p>
			<%} %>
			<%if(bean.getIndustryTypeId3() != 0){ %>
			<p>
				<em class="int_label">主体产业3:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId3()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称3:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId3()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale3() %>" /><%=bean.getIndustryUnit3()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience3() %>" />
			</p>
			<%} %>
			<p>
				<em class="int_label">上年度产业收入（万元）:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getLastYearIncome() %>" />
			</p>
		<%} else{%>
		    <p>
				<em class="int_label">产业所在地 ：</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>" />
			</p>
			
			<p>
				<em class="int_label">地区类型:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getAreaType(), FarmerIndustryInfoConfig.AREA_TYPE_MAP)%>" />
			</p>
			<p>
				<em class="int_label">经济区域类型:</em>
				<input size="50" disabled="disabled" type="text" value="<%=MapKit.getValueFromMap(bean.getEconomicAreaType(), FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_MAP)%>" />
			</p>
			<p>主体产业</p>
			<%if(bean.getIndustryTypeId1() != 0){ %>
			<p>
				<em class="int_label">主体产业1:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId1()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称1:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId1()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale1() %>" /><%=bean.getIndustryUnit1()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience1() %>" />
			</p>
			<% }%>
			<%if(bean.getIndustryTypeId2() != 0){ %>
			<p>
				<em class="int_label">主体产业2:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId2()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称2:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId2()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale2() %>" /><%=bean.getIndustryUnit2()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience2() %>" />
			</p>
			<%} %>
			<%if(bean.getIndustryTypeId3() != 0){ %>
			<p>
				<em class="int_label">主体产业3:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryTypeMap.get(bean.getIndustryTypeId3()).getTypeName()%>" />
			</p>
			<p>
				<em class="int_label">产业名称3:</em>
				<input size="50" disabled="disabled" type="text" value="<%=industryInfoMap.get(bean.getIndustryId3()).getIndustryName()%>" />
			</p>
			<p>
				<em class="int_label">产业规模:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getIndustryScale3() %>" /><%=bean.getIndustryUnit3()%>
			</p>
			<p>
				<em class="int_label">从事年限:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getExperience3() %>" />
			</p>
			<%} %>
			<p>
				<em class="int_label">上年度产业收入（万元）:</em>
				<input size="50" disabled="disabled" type="text" value="<%=bean.getLastYearIncome() %>" />
			</p>
		<%} %>		
		</div>
		<%} %>
		<div class="div_center">
		     <input type="button" name="button" id="button" value="返回" onclick="javascript:location.href='DeclareReportList.do'">		    
		</div>
</body>
</html>