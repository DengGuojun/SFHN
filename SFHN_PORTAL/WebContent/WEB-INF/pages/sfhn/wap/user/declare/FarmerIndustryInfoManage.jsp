<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*" %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.*" %>
<%@page import="com.lpmas.sfhn.declare.config.*"%>
<% 
	FarmerIndustryInfoBean bean = (FarmerIndustryInfoBean)request.getAttribute("FarmerIndustryInfoBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
	List<IndustryTypeBean> industryTypeList = (List<IndustryTypeBean>)request.getAttribute("IndustryTypeList");
	String originalIndustryName = (String)request.getAttribute("OriginalIndustryName");
	List<JobTypeBean> jobTypeList = (List<JobTypeBean>)request.getAttribute("JobTypeList");
	Map<Integer, List<JobInfoBean>> jobInfoMap = (Map<Integer, List<JobInfoBean>>)request.getAttribute("JobInfoMap");
	Map<Integer,String> jobIdMap = (Map<Integer,String>)request.getAttribute("JobIdMap");
	String title = "生产经营情况";
	if(declareType==DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER) title = "经营服务情况";
%>
<%@ include file="../../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
	<script src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
	<%-- <script type="text/javascript" src="<%=STATIC_URL %>js/main.js"></script> --%>
	<script type="text/javascript" src="<%=STATIC_URL %>js/plugin.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/new_main.js"></script>

    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/my.css">
    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/common.css">
	<title><%=title %></title>
</head>
<body>
	<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a><%=title %></div>
	<div class="placeHolder"></div>
	  <input type="hidden" name="declareId" id="declareId" value="<%=bean.getDeclareId()%>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>"/>
	  <input type="hidden" name="declareType" id="declareType" value="<%=declareType%>"/>
	  
     <!-- 类型为生产经营职业农民 -->
	  <%if(declareType == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER){ %>
	  <section class="info-table">
       <ul>
            <li id="person">
                <dd><i class="star">*</i>人员类别:</dd>
                <input id="person-input"  value="<%=MapKit.getValueFromMap(bean.getFarmerType(), FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_MAP)%>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
             <li class="address_add_pca clear">
                <label  class="col-2 left" for="newAddLocation"><i class="star">*</i>产业所在地:</label>
                <div class="col-10 cell" style="width:180px">
                    <input maxlength="25" type="hidden" name="province" id="industryProvince" autocomplete="off" data-verify="city" value="<%=bean.getIndustryProvince()%>">
                    <input maxlength="25" type="hidden" name="city" id="industryCity" autocomplete="off" data-verify="city" value="<%=bean.getIndustryCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="industryRegion" autocomplete="off" data-verify="city" value="<%=bean.getIndustryRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text" style="width:200px"  name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>">
                    </div>
                </div>
                <!-- <i class="col-2 cell"><b>*</b></i> -->
            </li>
            <li class="pca_select choice_address" id="pcaSelect">
                <p class="edit_title align_center">
                    <button type="button" class="cancel left"></button>
                    <b class="selected" data-content="pcaSelectProvince">省/直辖市</b>
                    <b data-content="pcaSelectCity">市</b>
                    <b data-content="pcaSelectRegion">区/县</b>
                </p>
                <div id="pcaSelectProvince"></div>
                <div id="pcaSelectCity"></div>
                <div id="pcaSelectRegion"></div>
            </li>
           <li id="industry" onclick="showIndustryLayer()" >
               <dd>主体产业:<i class="star">*</i></dd>
               <input id="industry-input" value="<%=originalIndustryName!=null ? originalIndustryName : "" %>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
            <li>
               <dd><i class="star">*</i><p>上年度产业收入（万元）:</p></dd>
               <dt style="width:130px"><input maxlength="15" placeholder="上年度产业收入（万元）"  name="lastYearIncome" id="lastYearIncome" value="<%=bean.getLastYearIncome() > 0 ? bean.getLastYearIncome() : ""%>"   type="number" /></dt>
           </li>
           <li class="hide-1 hasSecondChild" id="farm" >
            <dd>家庭农场情况:<i class="star">*</i></dd>
            <input id="farm-input" value="<%=MapKit.getValueFromMap(bean.getFamilyFarmType(), FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP)%>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png"/></span>
        </li>
       </ul>
    </section>
	 <%} %>
	    <!-- 类型为现代青年农场主 -->
	  <%if(declareType == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER){ %>
	  <section class="info-table">
       <ul>
            <li id="person">
                <dd><i class="star">*</i>人员类别:</dd>
                <input id="person-input"  value="<%=MapKit.getValueFromMap(bean.getFarmerType(), FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_MAP)%>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
             <li class="address_add_pca clear">
                <label  class="col-2 left" for="newAddLocation"><i class="star">*</i>产业所在地:</label>
                <div class="col-10 cell" style="width:180px">
                    <input maxlength="25" type="hidden" name="province" id="industryProvince" autocomplete="off" data-verify="city" value="<%=bean.getIndustryProvince()%>">
                    <input maxlength="25" type="hidden" name="city" id="industryCity" autocomplete="off" data-verify="city" value="<%=bean.getIndustryCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="industryRegion" autocomplete="off" data-verify="city" value="<%=bean.getIndustryRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text" style="width:200px"  name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>">
                    </div>
                </div>
                <!-- <i class="col-2 cell"><b>*</b></i> -->
            </li>
            <li class="pca_select choice_address" id="pcaSelect">
                <p class="edit_title align_center">
                    <button type="button" class="cancel left"></button>
                    <b class="selected" data-content="pcaSelectProvince">省/直辖市</b>
                    <b data-content="pcaSelectCity">市</b>
                    <b data-content="pcaSelectRegion">区/县</b>
                </p>
                <div id="pcaSelectProvince"></div>
                <div id="pcaSelectCity"></div>
                <div id="pcaSelectRegion"></div>
            </li>
            <li id="area-type">
               <dd><i class="star">*</i>地区类型:</dd>
               <input id="area-input"  value="<%=MapKit.getValueFromMap(bean.getAreaType(), FarmerIndustryInfoConfig.AREA_TYPE_MAP)%>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
           <li id="economic-type">
               <dd><i class="star">*</i>经济区域类型:</dd>
               <input id="economic-input" value="<%=MapKit.getValueFromMap(bean.getEconomicAreaType(), FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_MAP)%>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
           <li id="industry" onclick="showIndustryLayer()">
               <dd>主体产业:<i class="star">*</i></dd>
               <input id="industry-input" value="<%=originalIndustryName!=null ? originalIndustryName : "" %>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
            <li>
               <dd><i class="star">*</i><p>上年度产业收入（万元）:</p></dd>
               <dt style="width:130px"><input maxlength="15" placeholder="上年度产业收入（万元）"  name="lastYearIncome" id="lastYearIncome" value="<%=bean.getLastYearIncome() > 0 ? bean.getLastYearIncome() : ""%>"   type="number" /></dt>
           </li>
           <li class="hide-1 hasSecondChild" id="farm" >
            <dd>家庭农场情况:<i class="star">*</i></dd>
            <input id="farm-input" value="<%=MapKit.getValueFromMap(bean.getFamilyFarmType(), FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP)%>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png"/></span>
        </li>
       </ul>
    </section>
	 <%} %>
	 <!-- 类型为现代农业经营带头人 -->
	  <%if(declareType == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER){ %>
	  <input type="hidden" name="jobType" id="jobType" value="<%=bean.getJobType() > 0 ? bean.getJobType() : "" %>"/>
	  <input type="hidden" name="jobId" id="jobId" value="<%=bean.getJobId() > 0 ? bean.getJobId() : "" %>"/>
	  <section class="info-table">
       <ul>
            <li id="person">
                <dd><i class="star">*</i>人员类别:</dd>
                <input id="person-input"  value="<%=MapKit.getValueFromMap(bean.getFarmerType(), FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_MAP)%>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
             <li class="address_add_pca jobAddress clear hide-1 ">
                <label  class="col-2 left" for="newAddLocation"><i class="star">*</i>产业所在地:</label>
                <div class="col-10 cell" style="width:180px">
                    <input maxlength="25" type="hidden" name="province" id="industryProvince" autocomplete="off" data-verify="city" value="<%=bean.getIndustryProvince()%>">
                    <input maxlength="25" type="hidden" name="city" id="industryCity" autocomplete="off" data-verify="city" value="<%=bean.getIndustryCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="industryRegion" autocomplete="off" data-verify="city" value="<%=bean.getIndustryRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text" style="width:200px"  name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=bean.getIndustryProvince()%><%=bean.getIndustryCity()%><%=bean.getIndustryRegion()%>">
                    </div>
                </div>
                <!-- <i class="col-2 cell"><b>*</b></i> -->
            </li>
            <li class="pca_select choice_address" id="pcaSelect">
                <p class="edit_title align_center">
                    <button type="button" class="cancel left"></button>
                    <b class="selected" data-content="pcaSelectProvince">省/直辖市</b>
                    <b data-content="pcaSelectCity">市</b>
                    <b data-content="pcaSelectRegion">区/县</b>
                </p>
                <div id="pcaSelectProvince"></div>
                <div id="pcaSelectCity"></div>
                <div id="pcaSelectRegion"></div>
            </li>
           <li class="hide-1" id="industry" onclick="showIndustryLayer()">
               <dd>主体产业:<i class="star">*</i></dd>
               <input id="industry-input" value="<%=originalIndustryName!=null ? originalIndustryName : "" %>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
            <li class="hide-1" id="income">
               <dd><i class="star">*</i><p>上年度产业收入（万元）:</p></dd>
               <dt style="width:130px"><input maxlength="15" placeholder="上年度产业收入（万元）"  name="lastYearIncome" id="lastYearIncome" value="<%=bean.getLastYearIncome() > 0 ? bean.getLastYearIncome() : ""%>"   type="number" /></dt>
           </li>
           <li class="hide-1 hasSecondChild" id="farm">
            <dd>家庭农场情况:<i class="star">*</i></dd>
            <input id="farm-input" value="<%=MapKit.getValueFromMap(bean.getFamilyFarmType(), FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP)%>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png"/></span>
           </li>
            <li class="hide-1" id="worktype">
            <dd><i class="star">*</i>从事工作岗位:</dd>
            <input id="work-input" value="<%=jobIdMap.get(bean.getJobId()) == null ? "":jobIdMap.get(bean.getJobId()) %>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
            <li class="hide-1" id="size">
            <dd><i class="star">*</i>服务规模:</dd>
            <dt style="width:130px"><input maxlength="15" placeholder="请输入"  name="serviceScale" id="serviceScale" value="<%=bean.getServiceScale()%>" /></dt>
            </li>
            <li class="hide-1" id="worktime">
            <dd><i class="star">*</i>从业年限:</dd>
            <dt style="width:130px"><input maxlength="15" placeholder="年"  name="experience" id="experience" value="<%=bean.getExperience() > 0 ? bean.getExperience() : ""%>"   type="number" /></dt>
            </li>
            <li class="hide-1" id="yearincome">
            <dd><i class="star">*</i>年收入:</dd>
            <dt style="width:130px"><input maxlength="15" placeholder="万元"  name="yearIncome" id="yearIncome" value="<%=bean.getIncome() > 0 ? bean.getIncome() : "" %>"   type="number" /></dt>
            </li>
           
       </ul>
    </section>
	 <%} %>

<!-- 人员类型 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-person" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String farmerType = bean.getFarmerType(); 
	       List<StatusBean<String, String>> farmerTypeList = new ArrayList<StatusBean<String, String>>();
	       if(declareType == DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER){
	    	   farmerTypeList = FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST;
	       } else if(declareType == DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER){
	    	   farmerTypeList = FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_LIST;
	       } else if(declareType == DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER){
	    	   farmerTypeList = FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_LIST;
	       }%>	       
	    <%for(StatusBean<String,String> statusBean : farmerTypeList){ %>
			<li>
	            <input type="radio" name="farmerType" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(farmerType)) ? "id='radio-true' checked" : "id='radio-false'"%>><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(farmerType)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div>	 
<!-- 地区类型 -->
<div class="g-pb65 hide-1 fixed pop"  id="layer-area-type" >
<div class="placeHolder"></div>
    <ul class="form-s1">
        <%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.AREA_TYPE_LIST){ %>
    		<li>
      	    <input type="radio" name="areaType" id="radio-true" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus() == bean.getAreaType())?"checked":"" %>>
       		<label for="radio-true"><%=statusBean.getValue() %></label>
    		</li>
		<%} %>
    </ul>
</div>
<!-- 经济区域类型 -->
<div class="g-pb65 hide-1 fixed pop"  id="layer-economic-type" >
<div class="placeHolder"></div>
    <ul class="form-s1">
    		<%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_LIST){ %>
    		<li>
      	    <input type="radio" name="economicAreaType" id="radio-true" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus() == bean.getEconomicAreaType())?"checked":"" %>>
       		<label for="radio-true"><%=statusBean.getValue() %></label>
    		</li>
		<%} %>
    </ul>
</div>
<!-- 主体产业 -->
<div class="g-pb65 hide-1 fixed pop2"  id="layer-industry" >
<div class="placeHolder"></div>
    <ul class="form-s1">
        <li>
            <em><span>*</span>主体产业1:</em>
            <select class="industryType" id="industryTypeId1" name="industryTypeId1" >
            <%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
				<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId1() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
			<%} %>
            </select>
        </li>
        <li>
            <em><span>*</span>产业名称:</em>
            <input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId1() %>">
            <select class="industry" id="industryId1" name="industryId1" ></select>
        </li>
        <li>
            <em><span>*</span>产业规模:</em>
            <input type="number" name="industryScale1" id="industryScale1" value="<%=bean.getIndustryScale1() > 0 ? bean.getIndustryScale1() : "" %>" size="5" maxlength="5">
        </li>
        <li>
            <em><span>*</span>单位:</em>
            <select class="industryUnit" id="industryUnit1" name="industryTypeUnit1" >
				<option value="亩" <%=bean.getIndustryUnit1().equals("亩") ? "selected" : ""%>>亩</option>
				<option value="只/羽/头" <%=bean.getIndustryUnit1().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				<option value="公顷" <%=bean.getIndustryUnit1().equals("公顷") ? "selected" : ""%>>公顷</option>
            </select>
        </li>
        <li>
            <em><span>*</span>从事年限:</em>
            <input type="number" name="experience1" id="experience1" value="<%=bean.getExperience1() > 0 ? bean.getExperience1() : "" %>" size="5" maxlength="5">年
        </li>
    </ul>
    <ul class="form-s1" style="border-top: 10px solid #cccccc">
        <li>
            <em>主体产业2:</em>
            <select class="industryType" id="industryTypeId2" name="industryTypeI2" >
            		<option value=""></option>
 			<%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
				<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId2() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
			<%} %>
            </select>
        </li>
        <li>
            <em>产业名称2:</em>
            <input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId2() %>">
            <select class="industry" id="industryId2" name="industryId2" ></select>
        </li>
        <li>
            <em>产业规模:</em>
            <input type="number" name="industryScale2" id="industryScale2" value="<%=bean.getIndustryScale2() > 0 ? bean.getIndustryScale2() : ""%>"size="5" maxlength="5">
        </li>
        <li>
            <em>单位:</em>
            <select class="industryUnit" id="industryUnit2" name="industryTypeUnit2" >
				<option value="亩" <%=bean.getIndustryUnit2().equals("亩") ? "selected" : ""%>>亩</option>
				<option value="只/羽/头" <%=bean.getIndustryUnit2().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				<option value="公顷" <%=bean.getIndustryUnit2().equals("公顷") ? "selected" : ""%>>公顷</option>
            </select>
        </li>
        <li>
            <em>从事年限:</em>
            <input type="number" name="experience2" id="experience2" value="<%=bean.getExperience2() >0 ? bean.getExperience2() : ""%>"  size="5" maxlength="5">
        </li>
    </ul>
    <ul class="form-s1" style="border-top: 10px solid #cccccc">
        <li>
            <em>主体产业3:</em>
            <select class="industryType" id="industryTypeId3" name="industryTypeI3" >
            		<option value=""></option>
 			<%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
				<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == bean.getIndustryTypeId3() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
			<%} %>
            </select>
        </li>
        <li>
            <em>产业名称:</em>
            <input type="hidden" class="originalIndustryId" value="<%=bean.getIndustryId3() %>">
            <select class="industry" id="industryId3" name="industryId3" ></select>
        </li>
        <li>
            <em>产业规模:</em>
            <input type="number" name="industryScale3" id="industryScale3" value="<%=bean.getIndustryScale3() >0 ? bean.getIndustryScale3() : ""%>" size="5" maxlength="5">
        </li>
        <li>
            <em>单位:</em>
            <select class="industryUnit" id="industryUnit3" name="industryTypeUnit3" >
				<option value="亩" <%=bean.getIndustryUnit3().equals("亩") ? "selected" : ""%>>亩</option>
				<option value="只/羽/头" <%=bean.getIndustryUnit3().equals("只/羽/头") ? "selected" : ""%>>只/羽/头</option>
				<option value="公顷" <%=bean.getIndustryUnit3().equals("公顷") ? "selected" : ""%>>公顷</option>
            </select>
        </li>
        <li>
            <em>从事年限:</em>
            <input type="number" name="experience3" id="experience3" value="<%=bean.getExperience3() >0 ? bean.getExperience3() : ""%>"  size="5" maxlength="5">
        </li>
    </ul>
</div>
<!-- 家庭经营情况 -->
<div class="g-pb65 hide-1 fixed pop info-table no-auto"  id="layer-farm" >
    <div class="placeHolder"></div>
    <section>
        <p class="form-text ">是否登记注册？</p>

        <ul class="form-s1 train-form" >

            <li >
                <input type="radio" name="isRegistered"  id="register-true" value="<%=Constants.STATUS_VALID %>" <%=(bean.getHasRegisted()==Constants.STATUS_VALID)?"checked":"" %> >
                <label for="register-true">是</label>
            </li>
            <li  >
                <input type="radio" name="isRegistered" id="register-false" value="<%=Constants.STATUS_NOT_VALID %>" <%=(bean.getHasRegisted()==Constants.STATUS_NOT_VALID)?"checked":"" %>>
                <label for="register-false">否</label>
            </li>
        </ul>
    </section>
    <section>
        <p class="form-text ">是否是示范性家庭农场？</p>

        <ul class="form-s1 train-form demon" >

            <li >
                <input type="radio" name="isDemon"   value="<%=Constants.STATUS_VALID %>" <%=(bean.getIsExampleFamilyFarm()==Constants.STATUS_VALID)?"checked":"" %>>
                <label>是</label>
            </li>
            <li  >
                <input type="radio" name="isDemon"  value="<%=Constants.STATUS_NOT_VALID %>" <%=(bean.getIsExampleFamilyFarm()==Constants.STATUS_NOT_VALID)?"checked":"" %>>
                <label>否</label>
            </li>
        </ul>

    </section>
    <ul >
        <li id="farmgrade" class="hide-1 hasSecondChild">
            <dd style="width: 140px;"><i class="star">*</i>示范性农场级别:</dd>
            <input id="farmgrade-input" value="<%=MapKit.getValueFromMap(bean.getExampleFarmLevel(), FarmerIndustryInfoConfig.FARM_LEVEL_MAP)%>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
        </li>

        <li id="farmtype" class="hasSecondChild">
            <dd><i class="star">*</i>家庭农场类型:</dd>
            <input id="farmtype-input" value="<%=MapKit.getValueFromMap(bean.getFamilyFarmType(), FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_MAP)%>" type="text" disabled />
            <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
        </li>

        <li>
            <dd><i class="star">*</i>土地经营规模:</dd>
            <dt><input maxlength="10" placeholder="亩" name="farmLandScale" id="farmLandScale"  value="<%=bean.getFarmLandScale() >0.0 ? bean.getFamilyPerson() : "" %>"  type="number" /></dt>

        </li>

        <li>
            <dd><i class="star">*</i>家庭总人口:</dd>
           <dt><input maxlength="10" placeholder="人" name="familyPerson" id="familyPerson"  value="<%=bean.getFamilyPerson() >0 ? bean.getFamilyPerson() : "" %>"  type="number" /></dt>

        </li>

        <li>
               <dd><i class="star">*</i><p>家庭从事产业人数:</p></dd>
               <dt><input maxlength="10" placeholder="数量" name="familyWorkingPerson" id="familyWorkingPerson"  value="<%=bean.getFamilyWorkingPerson() >0 ? bean.getFamilyWorkingPerson() : "" %>"  type="number" /></dt>
           </li>
         <li>
               <dd><i class="star">*</i><p>上年度家庭收入（万元）:</p></dd>
               <dt style="width:100px"><input maxlength="15" placeholder="上年度家庭收入（万元）" name="lastYearFamilyIncome" id="lastYearFamilyIncome" value="<%=bean.getLastYearFamilyIncome() > 0 ? bean.getLastYearFamilyIncome() : "" %>" type="number" /></dt>
        </li>
    </ul>
</div>
<!-- 农场级别 -->
<div class="g-pb65 hide-1 fixed pop "  id="layer-farmgrade" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
         <%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FARM_LEVEL_LIST){ %>
    		<li>
      	    <input type="radio" name="farmgradeType" id="radio-true" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(bean.getExampleFarmLevel()))?"checked":"" %>>
       		<label for="radio-true"><%=statusBean.getValue() %></label>
    		</li>
		<%} %>
    </ul>
</div>

<!-- 农场类型 -->
<div class="g-pb65 hide-1 fixed pop "  id="layer-farmtype" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
         <%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_LIST){ %>
    		<li>
      	    <input type="radio" name="familyFarmType" id="radio-true" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(bean.getFamilyFarmType()))?"checked":"" %>>
       		<label for="radio-true"><%=statusBean.getValue() %></label>
    		</li>
		<%} %>
    </ul>
</div>
<!-- 工作岗位类别 -->
<div class="g-pb65 hide-1 fixed pop no-auto info-table"  id="layer-worktype" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
        <%for(JobTypeBean jobTypeBean: jobTypeList){ %>
        <li  class="hasSecondChild dynamic" data-name="works">
            <input type="hidden" disabled   value="<%=jobTypeBean.getTypeId()%>" >
            <label  ><%=jobTypeBean.getTypeName() %></label>
            <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
        </li>
	   <%} %>
    </ul>
</div>

<!-- 工作岗位 -->
<div class="g-pb65 hide-1 fixed pop back-root"  id="layer-works" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
    </ul>
</div>
<footer class="footer-all" id="produce-save-btn">保存</footer>
<footer class="footer-50">
<div class="footer-w50r"  onclick="hideFooter()" style="width:100%">保存</div></footer>
</body>
<script>
window.step = 1;
var edu = {};
var political = {};
var nation = {};
var personal = {};
var work = {};
var grade = {};
var work2 = {};
var grade2 = {};
var companytype = {};
var teckgrade = {};
var applyway = {};
var person= {
       <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.PRODUCT_FARMER_PERSONNEL_CATEGORY_LIST){%>
    	'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
       <%} %>
       <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.YOUNG_FARMER_PERSONNEL_CATEGORY_LIST){%>
       '<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
      <%} %>
      <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.LEADER_FARMER_PERSONNEL_CATEGORY_LIST){%>
  	   '<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
      <%} %>
 };
var farm= {
		 <%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FAMILY_FARM_TYPE_LIST){%>
	    	'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
	     <%} %>
};
var farmgrade= {
		<%for(StatusBean<String,String> statusBean : FarmerIndustryInfoConfig.FARM_LEVEL_LIST){%>
    	'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
        <%} %>
};
var area= {
		<%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.AREA_TYPE_LIST){%>
		'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var economic={
		<%for(StatusBean<Integer,String> statusBean : FarmerIndustryInfoConfig.ECONOMIC_AREA_TYPE_LIST){ %>
		'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var worktype= {
        <%for(JobTypeBean jobTypeBean: jobTypeList){ %>
		'<%=jobTypeBean.getTypeId()%>':'<%=jobTypeBean.getTypeName()%>',
		<%} %>
};
var dynamidData = function(){
     return {'works': {
    	 <% for (Integer key : jobInfoMap.keySet()) {%>
    	 '<%=key%>':{<%for(JobInfoBean jobInfoBean : jobInfoMap.get(key)){%>
    		 '<%=jobInfoBean.getJobId()%>':'<%=jobInfoBean.getJobName() %>',
    	 <%} %>
             },
 		<%} %>
            }
      }
};
$(document).ready(function() {
	 var child = "";
	 var declareType = "<%=declareType%>";
	 var personInput = $('input[name="farmerType"]:checked').val();
	 //如果是示范性，对应的要显示
	 <%if(bean.getIsExampleFamilyFarm()==Constants.STATUS_VALID){%>
	 	$('#farmgrade').removeClass("hide-1");
	 <%}%>
	 
	 //对带头人做特殊处理
	 if(declareType=="<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER%>"){
		 if(personInput == '<%=FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER%>'){
			 $(".jobAddress").show();
			 $('#industry').show();
			 $("#income").show();
			 $("#farm").attr("style","display : list-item");
			 $(".demon").trigger('click');
		 }else if(personInput == '<%=FarmerInfoConfig.PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER%>'){
			 $(".jobAddress").show();
			 $(".jobAddress").find("label").html(" <i class='star'>*</i>工作地点:");
			 $("#worktype").show();
			 $("#size").show();
			 $("#worktime").show();
	         $("#yearincome").show();
		 }else if(personInput!=''&&typeof(personInput)!="undefined"){
			 $('#industry').show();
			 $("#income").show();
			 $(".jobAddress").show();
		 }
	 }else{
		 if(personInput == '<%=FarmerInfoConfig.PERSONNEL_CATEGORY_FAMILY_FARMER%>'){
			 $("#farm").attr("style","display : list-item");
			 $(".demon").trigger('click');
		 }else if(personInput == '<%=FarmerInfoConfig.PERSONNEL_CATEGORY_AGRICULTURAL_SERVICE_ORGANIZER%>'){
			 $("#worktype").show();
			 $(".jobAddress").show();
			 $(".jobAddress").find("label").html(" <i class='star'>*</i>工作地点:");
	         $("#size").show();
	         $("#worktime").show();
	         $("#yearincome").show();
		 }
	 }
     var industry;
     $("#layer-industry").find("select.industryType").on("change",function(){
         var typeId = $(this).find("option:selected").val();
         seletCate(typeId,$(this));
     });
     $("#layer-industry").find("select.industryType").trigger('change');
     function seletCate(typeId,element){
        	$.ajax({
    	        type: 'get',
    	        url: "/sfhn/IndustryInfoJsonList.do?typeId="+typeId,
    	        dataType: 'json',
    	        success: function(data){
    	        		var industry=data.result;
    	        		child ="";
                 for(var j=0;j< industry.length;j++){
                	 	var originalId = element.parent().parent().find(".originalIndustryId").val();
                	 	if(industry[j].industryId == originalId){
                	 		child += "<option value='" + industry[j].industryId  +"' selected>"+ industry[j].industryName + "</option>"
                	 	}else{
                	 		child += "<option value='" + industry[j].industryId  +"'>"+ industry[j].industryName + "</option>"
                	 	}
                	 	//由于对应于同一产业类型的产业，目前他们的单位都一致，所以在这里设置单位
                	 	var unit = industry[j].unit;
                	 	if(unit==''){
                	 		element.parent().parent().find(".industryUnit").removeAttr('disabled');
                	 	}else{
                	 		//element.parent().parent().find(".industryUnit").find("option[value='"+unit+"']").attr("selected","selected");
                	 		element.parent().parent().find(".industryUnit").val(unit)
                    	 	element.parent().parent().find(".industryUnit").attr("disabled","disabled");
                	 	}
                 }
                 element.parent().parent().find(".industry").html(child);
    	        },
    	        error: function(){
    	            return;
    	        }
    	    });
     }
 });


$('#produce-save-btn').on('click',function(){
	console.log(window.step);
    if(window.step == 2){
        window.step = 1;
        var status = $(".pop2").css("display");

        if (status == 'block'){
            $(".pop2").attr("style","display : none");
            $(".footer-50").attr("style","display : none");
            $(".footer-all").attr("style","display : block");
        }
        else if ($(".pop").hasClass("show-1")){
            $(".pop").removeClass("show-1").addClass("hide-1")
            $(".footer-all").attr("style","display : block");
        }
        else{
            //window.location.href='DeclareInfoManage.do?declareType=2';
        }
        $("#farm-input").val($("#farmtype-input").val());
        return;
    }
    else if(window.step == 1){
        var re = /^[0-9]+[0-9]*]*$/;
    	var farmerType = $('input[name="farmerType"]:checked').val();
    	var declareType = $('#declareType').val();
        var status = $('#status').val();
        var declareId = $('#declareId').val();
        var farmLandScale = $('#farmLandScale').val();
        var familyWorkingPerson = $('#familyWorkingPerson').val();
        var familyPerson = $('#familyPerson').val();
        var areaType = $('input[name="areaType"]:checked').val();
        var economicAreaType = $('input[name="economicAreaType"]:checked').val();
        var industryTypeId1 = $('#industryTypeId1').val();
        var industryId1 = $('#industryId1').val();
        var industryScale1 = $('#industryScale1').val();
        var experience1 = $('#experience1').val();
        var industryUnit1 = $('#industryUnit1').val();
        var industryTypeId2 = $('#industryTypeId2').val();
        var industryId2 = $('#industryId2').val();
        var industryScale2 = $('#industryScale2').val();
        var experience2 = $('#experience2').val();
        var industryUnit2 = $('#industryUnit2').val();
        var industryTypeId3 = $('#industryTypeId3').val();
        var industryId3 = $('#industryId3').val();
        var industryScale3 = $('#industryScale3').val();
        var experience3 = $('#experience3').val();
        var industryUnit3 = $('#industryUnit3').val();
        var lastYearIncome = $('#lastYearIncome').val();
        var lastYearFamilyIncome = $('#lastYearFamilyIncome').val();
        var industryProvince = $('#industryProvince').val();
		var industryCity = $('#industryCity').val();
		var industryRegion = $('#industryRegion').val();
		var isRegistered = $('input[name="isRegistered"]:checked').val();
		var isDemon = $('input[name="isDemon"]:checked').val();
		var farmgradeType = $('input[name="farmgradeType"]:checked').val();
		var familyFarmType = $('input[name="familyFarmType"]:checked').val();
		var serviceScale = $('#serviceScale').val();
	    var experience = $('#experience').val();
	    var income = $('#yearIncome').val();
	    var jobId = $('#jobId').val();
	    var jobType = $('#jobType').val();
        if(declareType == '<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER%>'){
        	if (farmerType == ''){
    			alert("人员类别必须填写");
    			return;
    		}
        	else if(farmerType == 'FAMILY_FARMER'){
    		    if(typeof(isRegistered)=="undefined"){
		    		alert("请填写[是否登记注册]")
		    		return false;
		       }
		        if(typeof(isDemon)=="undefined"){
	    		alert("请填写[是否示范性家庭农场]")
	    		return false;
	           }
		       if(isDemon == '<%=Constants.STATUS_VALID%>'){
		    	if(farmgradeType == ''||typeof(farmgradeType)=="undefined"){
		    		alert("示范性农场级别必须填写");
	      			return;
		    	 }
		       }
		       if(familyPerson == ''){
	    		alert("家庭总人口必须填写");
      			return;
	    	    }
		        if(familyWorkingPerson == ''){
	    		alert("家庭从事产业人数必须填写");
      		 	return;
	    	   }
		       if (familyPerson!="" &&!re.test(familyPerson)){
		        alert("[家庭总人口]请输入数字");
		        return false;
		       }else if(familyWorkingPerson!="" && !re.test(familyWorkingPerson)){
		   	 	alert("[家庭从事产业人数]请输入数字");
		        return false;
		       }
		        if(familyPerson == ''){
	    		alert("家庭总人口必须填写");
      			return;
	    	   }
		       if (farmLandScale == ''){
    			alert("土地经营规模必须填写");
    			return;
    		   }
		       if(familyFarmType == ''){
		    		alert("家庭农场类型必须填写");
	      		 	return;
		       }
		       if (lastYearFamilyIncome == ''){
	     			alert("上年度家庭收入必须填写");
	     			return;
	     		}
		   }
        	if (industryProvince == '' || industryRegion == '' || industryCity == ''){
    			alert("产业所在地必须填写");
    			return;
    		}
        	if (areaType == ''){
    			alert("地区类型必须填写");
    			return;
    		}
        	if (economicAreaType == ''){
    			alert("经济区域类型必须填写");
    			return;
    		}
        	 if (industryTypeId1 == '' || industryId1 == '' || industryScale1 == '' || experience1 == '' || industryUnit1 == '' ){
     			alert("主体产业1必须填写");
     			return;
     		}
        	if (lastYearIncome == ''){
     			alert("上年度产业收入必须填写");
     			return;
     		}
        	
        }
        if(declareType == '<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER%>'){
        	if (farmerType == ''){
    			alert("人员类别必须填写");
    			return;
    		}
        	else if(farmerType == 'FAMILY_FARMER'){
    		    if(typeof(isRegistered)=="undefined"){
		    		alert("请填写[是否登记注册]")
		    		return false;
		       }
		        if(typeof(isDemon)=="undefined"){
	    		alert("请填写[是否示范性家庭农场]")
	    		return false;
	           }
		       if(isDemon == '<%=Constants.STATUS_VALID%>'){
		    	if(farmgradeType == ''||typeof(farmgradeType)=="undefined"){
		    		alert("示范性农场级别必须填写");
	      			return;
		    	 }
		       }
		       if(familyPerson == ''){
	    		alert("家庭总人口必须填写");
      			return;
	    	    }
		        if(familyWorkingPerson == ''){
	    		alert("家庭从事产业人数必须填写");
      		 	return;
	    	   }
		       if (familyPerson!="" &&!re.test(familyPerson)){
		        alert("[家庭总人口]请输入数字");
		        return false;
		       }else if(familyWorkingPerson!="" && !re.test(familyWorkingPerson)){
		   	 	alert("[家庭从事产业人数]请输入数字");
		        return false;
		       }
		        if(familyPerson == ''){
	    		alert("家庭总人口必须填写");
      			return;
	    	   }
		       if (farmLandScale == ''){
    			alert("土地经营规模必须填写");
    			return;
    		   }
		       if(familyFarmType == ''){
		    		alert("家庭农场类型必须填写");
	      		 	return;
		       }
		       if (lastYearFamilyIncome == ''){
	     			alert("上年度家庭收入必须填写");
	     			return;
	     		}
		   }
        	if (industryProvince == '' || industryRegion == '' || industryCity == ''){
    			alert("产业所在地必须填写");
    			return;
    		}
        	 if (industryTypeId1 == '' || industryId1 == '' || industryScale1 == '' || experience1 == '' || industryUnit1 == '' ){
      			alert("主体产业1必须填写");
      			return;
      		}
         	if (lastYearIncome == ''){
      			alert("上年度产业收入必须填写");
      			return;
      		}
        }
        if(declareType == '<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER%>'){
        	if (farmerType == ''){
    			alert("人员类别必须填写");
    			return;
    		}else if(farmerType == 'FAMILY_FARMER'){
    		    if(typeof(isRegistered)=="undefined"){
    		    		alert("请填写[是否登记注册]")
    		    		return false;
    		    }
    		    if(typeof(isDemon)=="undefined"){
		    		alert("请填写[是否示范性家庭农场]")
		    		return false;
		        }
    		    if(isDemon == '<%=Constants.STATUS_VALID%>'){
    		    	if(farmgradeType == ''||typeof(farmgradeType)=="undefined"){
    		    		alert("示范性农场级别必须填写");
    	      			return;
    		    	}
    		    }
    		    if(familyPerson == ''){
		    		alert("家庭总人口必须填写");
	      			return;
		    	}
    		    if(familyWorkingPerson == ''){
		    		alert("家庭从事产业人数必须填写");
	      			return;
		    	}
    		    if (familyPerson!="" &&!re.test(familyPerson)){
    		        alert("[家庭总人口]请输入数字");
    		        return false;
    		    }else if(familyWorkingPerson!="" && !re.test(familyWorkingPerson)){
    		   	 	alert("[家庭从事产业人数]请输入数字");
    		        return false;
    		    }
    		    if(familyPerson == ''){
		    		alert("家庭总人口必须填写");
	      			return;
		    	}
    		    if (farmLandScale == ''){
        			alert("土地经营规模必须填写");
        			return;
        		}
    		    if(familyFarmType == ''){
		    		alert("家庭农场类型必须填写");
	      		 	return;
		       }
    		   if (lastYearFamilyIncome == ''){
	     			alert("上年度家庭收入必须填写");
	     			return;
	     		}
    		   if (industryTypeId1 == '' || industryId1 == '' || industryScale1 == '' || experience1 == '' || industryUnit1 == '' ){
	      			alert("主体产业1必须填写");
	      			return;
	      		}
    		}else if(farmerType == 'AGRICULTURAL_SERVICE_ORGANIZER'){
    			 if (serviceScale == ''){
         			alert("服务规模必须填写");
         			return;
         		}
    			 if (experience == ''){
         			alert("从业年限必须填写");
         			return;
         		}
    			 if (income == ''){
         			alert("年收入必须填写");
         			return;
         		}
         		if (jobId == ''){
         			alert("从事工作岗位必须填写");
         			return;
         		}
    		}else{
    			if (industryProvince == '' || industryRegion == '' || industryCity == ''){
        			alert("产业所在地必须填写");
        			return;
        		}
    			if (industryTypeId1 == '' || industryId1 == '' || industryScale1 == '' || experience1 == '' || industryUnit1 == '' ){
    	      			alert("主体产业1必须填写");
    	      			return;
    	      	}
    	        if (lastYearIncome == ''){
    	      			alert("上年度产业收入必须填写");
    	      			return;
    	      	}
    		}
        }
        $.ajax({
            url: 'FarmerIndustryInfoManage.do',
            data: {
                declareType: declareType,
                familyFarmType: familyFarmType,
                farmerType:farmerType,
                status: status,
                declareId: declareId,
                familyWorkingPerson: familyWorkingPerson,
                familyPerson: familyPerson,
                farmLandScale: farmLandScale,
                areaType: areaType,
                economicAreaType: economicAreaType,
                industryTypeId1: industryTypeId1,
                industryId1: industryId1,
                industryScale1: industryScale1,
                experience1: experience1,
                industryUnit1: industryUnit1,
                industryTypeId2: industryTypeId2,
                industryId2: industryId2,
                industryScale2: industryScale2,
                experience2: experience2,
                industryUnit2: industryUnit2,
                industryTypeId3: industryTypeId3,
                industryId3: industryId3,
                industryScale3: industryScale3,
                experience3: experience3,
                industryUnit3: industryUnit3,
                lastYearIncome: lastYearIncome,
                lastYearFamilyIncome: lastYearFamilyIncome,
                industryProvince: industryProvince,
                industryCity: industryCity,
                industryRegion: industryRegion,
                hasRegisted: isRegistered,
                isExampleFamilyFarm: isDemon,
                exampleFarmLevel : farmgradeType,
                income: income,
                serviceScale:serviceScale,
                experience:experience,
                jobId:jobId,
                jobType:jobType,
            },
            type: 'POST',
            success: function (json) {
                console.log(json);
                json = ajaxAuthCheck(json);
                if (json.code == '1') {
                    window.location = 'DeclareInfoManage.do?declareType='+declareType
                }
                else if (json.code == '0') {
                    alert(json.message);
                }
            }
        })
    }
});

(function(){
    te$.address.initPca('addressDetail', 'pcaSelect');


    $(".addr_choose").on("click tap",function(){
        var target = $(this);
        var id = target.data("id");
        if(target.hasClass("addr_close")){
            $.ajax({
                url:"/user/UserAddressDefaultJson.do?addressId=" + id,
                type:"GET",
                error:function(msg){
                    alert(msg)
                },
                success:function(result){
                    if(result.code == "1"){
                        $(".addr_open").removeClass("addr_open").addClass("addr_close");
                        target.removeClass("addr_close").addClass("addr_open");

                    }
                }
            })
        }

    })
    
      //是否是示范性家庭农场
        $(".demon").find("input").on("click",function(){ 
            var val = $(this).val();
            if(val == "1"){

                if($("#farmgrade").hasClass("hide-1")){
                    $("#farmgrade").removeClass("hide-1")
                }

            }
            else{
                if(!$("#farmgrade").hasClass("hide-1"))
                    $("#farmgrade").addClass("hide-1")
            }
        })
})();

function showIndustryLayer(){
	$(".footer-50").show();
	$("#layer-industry").show();
}
function hideFooter(){
	$(".footer-50").hide();
	$("#layer-industry").hide();
	$(".footer-all").show();
	var industryName =  $("#industryId1").find("option:selected").text();
	$("#industry-input").val(industryName);
}

function toolBarBack(){
    var status = $(".pop2").css("display");
    if (status == 'block'){
        $(".pop2").attr("style","display : none");
        $(".footer-50").attr("style","display : none");
        $(".footer-all").attr("style","display : block");
    }
    else if ($(".pop").hasClass("show-1")){
        $(".pop").removeClass("show-1").addClass("hide-1")
        $(".footer-all").attr("style","display : block");
    }
    else{
    	window.location.href='DeclareInfoManage.do?declareType=<%=declareType%>';
    }
}
</script>
</html>