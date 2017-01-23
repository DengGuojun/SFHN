<%@page import="com.lpmas.sfhn.declare.config.FarmerInfoConfig"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*" %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.sfhn.declare.config.DeclareInfoConfig" %>
<%@ page import="com.lpmas.sfhn.declare.bean.FarmerSkillInfoBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.NationalCertificationBean" %>
<%@ page import="com.lpmas.sfhn.declare.config.FarmerSkillInfoConfig" %>
<% 
	FarmerSkillInfoBean bean = (FarmerSkillInfoBean)request.getAttribute("FarmerSkillInfoBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
	List<NationalCertificationBean> ncList = (List<NationalCertificationBean>)request.getAttribute("NationalCertificationList");
	Map<Integer, String> ncMap = (Map<Integer, String>)request.getAttribute("NationalCertificationMap");
%>
<%@ include file="../../../../include/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
    <script src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/new_main.js"></script>
	<title>申请培训信息</title>
</head>
<body>
	<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>申请培训信息</div>
	<div class="placeHolder"></div>
	  <input type="hidden" name="declareId" id="declareId" value="<%=bean.getDeclareId()%>"/>
	  <input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>"/>
	  <input type="hidden" name="declareType" id="declareType" value="<%=declareType%>"/>
	  <div class="g-pb65 show-1">
		    <section class="info-table" style="background: none">
		        <ul class="form-s1 qualifications">
		
		            <li id="applyway" >
		                <a href="javascript:void(0)"><dd><i class="star">*</i>申请方式:<span></span></dd>
		                    <input id="applyway-input" value="<%=MapKit.getValueFromMap(bean.getApplyType(), DeclareInfoConfig.APPLY_TYPE_MAP) %>" type="text" disabled />
		                <span><img src="<%=STATIC_URL %>/images/icon_go.png" /></span></a>
		            </li>
		
		        </ul>
		        <ul class="form-s1 qualifications" style="margin-top: 10px">
		
		            <li id="experience">
		                <dd><i class="star">*</i>学习培训经历:<span></span></dd>
		                <input type="text" id="input-experience" value="">
		                <span><img src="<%=STATIC_URL %>/images/icon_go.png" /></span>
		            </li>
		            <li id="certification">
		                <dd><i class="star">*</i>获得证书情况:<span></span></dd>
		                <input type="text" id="input-cert" disabled value="<%=bean.getHasNewTypeCertification()==(Constants.STATUS_VALID) ? "已获得证书" : "未获得证书"%>">
		                <span><img src="<%=STATIC_URL %>/images/icon_go.png" /></span>
		            </li>
		        </ul>
		    </section>
	</div>

<!-- 申请方式 -->
<div class="g-pb65 hide-1 fixed"  id="layer-applyway" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
    	<%for(StatusBean<String,String> statusBean : DeclareInfoConfig.APPLY_TYPE_LIST){ %>
		 	<li>
	            <input type="radio" id="applyType"  name="applyType"  value="<%=statusBean.getStatus()%>" <%=statusBean.getStatus().equals(bean.getApplyType())? "id='radio-true' checked" : "id='radio-false'"%>>
	            <label for="<%=statusBean.getStatus().equals(bean.getApplyType()) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue()%></label>
        	</li>
		<%} %>
    </ul>
</div>

<!-- 是否参加过新型职业农民培训 -->
<div class="g-pb65 hide-1 fixed no-auto"  id="layer-experience" >
    <section>
        <p class="form-text">是否参加过新型职业农民培训？</p>
        <ul class="form-s1 train-form" style="margin-top: 20px">
        <%for(StatusBean<Integer,String> statusBean : Constants.STATUS_LIST){ %>
        	<li>
	            <input type="radio"  name="isTrained"  value="<%=statusBean.getStatus()%>" <%=statusBean.getStatus()==bean.getIsTrained()? "id='radio-true' checked" : "id='radio-false'"%>>
	            <label for="<%=statusBean.getStatus()==bean.getIsTrained() ? "radio-true" : "radio-false"%>"><%=statusBean.getStatus()==Constants.STATUS_VALID ? "参加过" : "未参加过"%></label>
        	</li>
        <%} %>
        </ul>
        <ul  class="form-s1 hide-1 other-train" >
            <li><i class="star">*</i>
                参加其他农业培训
                <input type="text" name="otherTrainingTime" id="otherTrainingTime" placeholder="培训次数" value="<%=bean.getOtherTrainingTime()%>">
                次／年
            </li>
        </ul>
    </section>
</div>

<!-- 是否获得新型职业农民资格证书 -->
<div class="g-pb65 hide-1 fixed no-auto"  id="layer-certification" >
    <div class="placeHolder"></div>
    <p class="form-text">是否获得新型职业农民资格证书？</p>
    <ul class="form-s1 train-form" style="margin-top: 20px">
       <%for(StatusBean<Integer,String> statusBean : Constants.STATUS_LIST){ %>
        	<li>
	            <input type="radio"  name="isCert"  value="<%=statusBean.getStatus()%>" <%=statusBean.getStatus()==bean.getHasNewTypeCertification()? "id='radio-true' checked" : "id='radio-false'"%>>
	            <label for="<%=statusBean.getStatus()==bean.getHasNewTypeCertification() ? "radio-true" : "radio-false"%>"><%=statusBean.getStatus()==(Constants.STATUS_VALID) ? "是" : "否"%></label>
        	</li>
        <%} %>
    </ul>
    
    <section class="info-table">
        <ul class="form-s1 qualifications hide-1 cert-option">
            <li id="grade" class="hasSecondChild">
                <dd><i class="star">*</i>认定等级:<span></span></dd>
                <input id="grade-input" value="<%=MapKit.getValueFromMap(bean.getCertificationGrade(), FarmerInfoConfig.CERTIFICATION_LEVEL_MAP)%>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
            <li id="time" class="hasSecondChild">
                <dd><i class="star">*</i>认定时间:<span></span></dd>
                <input id="time-input" value="<%=bean.getCertificationDate()==null? "":DateKit.formatTimestamp(bean.getCertificationDate(), DateKit.DEFAULT_DATE_FORMAT) %>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
            <li id="department">
                <dd><i class="star">*</i>认定部门:<span></span></dd>
                <input id="department-input" name="certificationDepartment" value="<%=bean.getCertificationDepartment() %>" type="text" />
            </li>
        </ul>
        <div style="margin-top: 10px; background: #eeeeee; padding:10px 0 10px 15px">其他农业培训证书</div>
        <ul class="form-s1 qualifications cert">
            <li>
                <label >未获得证书</label>
                <span class="hide-1 no-cert" id="nev-cert"></span>
                <input type="hidden" name="hasNoCertification" id="hasNoCertification" value="<%=Constants.STATUS_VALID%>" <%=Constants.STATUS_VALID == bean.getHasNoCertification() ? "checked" : ""%>>
            <li>
                <label>新型职业农民培训证书</label>
                <span class="hide-1 has-cert" id="train-cert"></span>
                <input type="hidden" name="hasNewTypeTrainingCertification" id="hasNewTypeTrainingCertification" value="<%=Constants.STATUS_VALID%>" <%=Constants.STATUS_VALID == bean.getHasNewTypeTrainingCertification() ? "checked" : ""%>>
            <li>
                <label>国家职业资格证书</label>
                <span class="hide-1 has-cert" id="occu-cert"></span>
                <input type="hidden" name="hasNationalCertification" id="hasNationalCertification" value="<%=Constants.STATUS_VALID%>" <%=Constants.STATUS_VALID == bean.getHasNationalCertification() ? "checked" : ""%> >
        </ul>
        <!--<div class="c-5da councity-book" style>+ 添加国家职业资格证书-->
        <ul class="form-s1 teck-grade">
            <li id="teckgrade" class="hasSecondChild">
                <label>农民技术等级或职称证书:<span></span></label>
                <input id="teckgrade-input" value="<%=MapKit.getValueFromMap(bean.getCertificationTitle(), FarmerInfoConfig.FARMER_TITLE_MAP) %>" type="text" disabled />
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
        </ul>
</section>
</div>

<!-- 农民技术等级 -->
<div class="g-pb65 hide-1 fixed"  id="layer-teckgrade" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
    	<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.FARMER_TITLE_LIST){ %>
    		<li>
            <input type="radio" name="certificationTitle"  value="<%=statusBean.getStatus() %>" <%=statusBean.getStatus().equals(bean.getCertificationTitle()) ? "checked":""%>>
            <label for="<%=statusBean.getStatus().equals(bean.getCertificationTitle()) ? "radio-true":"radio-false"%>"><%=statusBean.getValue() %></label>
        	</li>
    	<%} %>
    </ul>
</div>
<!-- 等级 -->
<div class="g-pb65 hide-1 fixed"  id="layer-grade" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
        <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.CERTIFICATION_LEVEL_LIST){ %>
	    <li>
	       <input type="radio" name="certificationGrade" id="radio-true" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(bean.getCertificationGrade()))?"checked":"" %>>
	       <label for="<%=statusBean.getStatus().equals(bean.getCertificationGrade()) ? "radio-true":"radio-false"%>"><%=statusBean.getValue() %></label>
	    </li>
		<%} %>
    </ul>
</div>

<!-- 等级 -->
<div class="g-pb65 hide-1 fixed no-auto"  id="layer-time" >
    <div class="placeHolder"></div>
    <ul class="form-s1">
        <li>
            <dd>日期:</dd>
            <input type="date"  id="certificationDate" name="certificationDate" data-verify="birthday" value="<%=bean.getCertificationDate()==null? "":DateKit.formatTimestamp(bean.getCertificationDate(), DateKit.DEFAULT_DATE_FORMAT) %>" autocomplete="off">
        </li>
    </ul>
</div>

<footer class="footer-all" id="tech-save-btn">保存</footer>
<footer class="footer-50">
    <div class="footer-w50l">取消</div>
    <div class="footer-w50r">保存</div></footer>
</body>
<script>
window.step = 1;
var edu = {};
var political = {};
var nation = {};
var personal = {};
var area= {};
var economic={};
var companytype = {};
var work = {
		<%for(NationalCertificationBean ncBean : ncList){ %>
			'<%=ncBean.getCertificateId()%>':'<%=ncBean.getCertificateName()%>',
		<%} %>
};
var grade = {
		<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.CERTIFICATION_LEVEL_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var work2 = {
		<%for(NationalCertificationBean ncBean : ncList){ %>
			'<%=ncBean.getCertificateId()%>':'<%=ncBean.getCertificateName()%>',
		<%} %>
};
var grade2 = {
		<%for(StatusBean<Integer,String> statusBean : FarmerSkillInfoConfig.SKILL_GRADE_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var applyway = {
	<%for(StatusBean<String,String> statusBean : DeclareInfoConfig.APPLY_TYPE_LIST){ %>
		'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
	<%} %>
};
var teckgrade= {
	<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.FARMER_TITLE_LIST){ %>
		'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
	<%} %>
    };
    
$('.footer-all').on('click',function(){
    var layerTime = $("#layer-time");
    var layerApplyway = $("#layer-applyway");
    var layerExperience = $("#layer-experience");
    var layerCertification = $("#layer-certification");
    var val = layerTime.find("input").val();
    var isChecked = 1;
    if(layerTime.hasClass("show-1")){
        if(val == "")
        {
            alert("请输入时间");
            isChecked = isChecked & 0
            return false;
        }
        else{
            $("#time-input").val(val);
            isChecked = isChecked & 1;
            layerTime.removeClass("show-1").addClass("hide-1");

        }

    }
    else {
        if($("#applyway-input").val() == "" && !layerExperience.hasClass("show-1") && !layerCertification.hasClass("show-1")){
            alert("请填写申请方式!")
            isChecked = isChecked & 0;
        }
    }


    if(layerExperience.hasClass("show-1")){
        var re = /^[0-9]+[0-9]*]*$/;
        var number = $('#otherTrainingTime').val();
        if (!re.test(number)){
            alert("[参加其他培训次数]请输入数字");
            isChecked = isChecked & 0
            return false;
        }
        isChecked = isChecked & 1;
        //$("#input-experience").val($("#otherTrainingTime").val() + "年")
        layerExperience.removeClass("show-1").addClass("hide-1")

    }
    else {
        if($("input-experience").val() == "" && !layerApplyway.hasClass("show-1") && !layerCertification.hasClass("show-1")){
            alert("请填写[是否参加过新型职业农民培训]")
            isChecked = isChecked & 0;
        }
    }





    if($("#layer-certification").hasClass("show-1")){
        var isCert = $('input[name="isCert"]:checked').val();
        if(window.step == 2){
            if(isCert == "1"){
                if($("#grade-input").val() == ""){
                    alert("请输入等级");
                    isChecked = isChecked & 0;
                    return false;
                }
                if($("#time-input").val() == ""){
                    alert("请输入认定时间");
                    isChecked = isChecked & 0;
                    return false;
                }

                if($("#department-input").val() == ""){
                    alert("请输入认定部门");
                    isChecked = isChecked & 0;
                    return false;
                }
                $("#input-cert").val("已获得证书");
                isChecked = isChecked & 1;
            }
            else{
                $("#input-cert").val("未获得证书")
                isChecked = isChecked & 1;
            }
            layerCertification.removeClass("show-1").addClass("hide-1")
        }

    }

    if(window.step == 3) {
        window.step = 2;
        return;
    }

    if(window.step == 2) {
        window.step = 1;
        return;
    }

    var isCert = $("#input-cert").val();
    var isTrained = $('input[name="isTrained"]:checked').val();
    var applyWay = $("#applyway-input").val();
    if(window.step == 1){
        if(isCert == "" ){
            alert("请填写获得证书情况");
            isChecked = isChecked & 0;
            return;
        }

        if(applyWay == "" ){
            alert("请填写申请方式");
            isChecked = isChecked & 0;
            return;
        }

        if(typeof(isTrained)=="undefined"){
            alert("请填写[是否参加过新型职业农民培训]")
            isChecked = isChecked & 0;
            return false;
        }
    }
            if(isChecked){
            	submit();
            }
});

function submit(){
	var isTrained = $('input[name="isTrained"]:checked').val();
    if(typeof(isTrained)=="undefined"){
    		alert("请填写[是否参加过新型职业农民培训]")
    		return false;
    }
    var re = /^[0-9]+[0-9]*]*$/;
    var number = $('#otherTrainingTime').val();
    if (!re.test(number)){
        alert("[参加其他培训次数]请输入数字");
        return false;
    }else {
        var declareType = $('#declareType').val();
        var status = $('#status').val();
        var declareId = $('#declareId').val();
        var applyType = $('input[name=applyType]:checked').val();
        var certificationGrade = $('input[name=certificationGrade]:checked').val();
        var certificationDate = $('input[name=certificationDate]').val()+' 00:00:00';
        var certificationDepartment = $('input[name=certificationDepartment]').val();
        var otherTrainingTime = $('#otherTrainingTime').val();
        var certificationTitle = $('input[name=certificationTitle]:checked').val();
        var hasNewTypeCertification = $('input[name=isCert]:checked').val();
        var hasNoCertification = 0;
        if (typeof($("#hasNoCertification").attr("checked"))!="undefined") {
        		hasNoCertification = 1;
        }
        var hasNewTypeTrainingCertification = 0;
        if (typeof($("#hasNewTypeTrainingCertification").attr("checked"))!="undefined") {
        		hasNewTypeTrainingCertification = 1;
        }
        var hasNationalCertification = 0;
        if (typeof($("#hasNationalCertification").attr("checked"))!="undefined") {
        	hasNationalCertification = 1;
        }
        
      if(typeof(applyType)=="undefined"||applyType.trim()==""){
        	alert("申请类型必须填写");
        	return false;
        }
        
        if(hasNewTypeCertification=="<%=Constants.STATUS_VALID%>"){
        	if(typeof(certificationGrade)=="undefined"||certificationGrade.trim()==""){
            	alert("认证等级必须填写");
            	return false;
            }else if(typeof(certificationDate)=="undefined"||certificationDate.trim()==""){
            	alert("认证时间必须填写");
            	return false;
            }else if(typeof(certificationDepartment)=="undefined"||certificationDepartment.trim()==""){
            	alert("认证部门必须填写");
            	return false;
            }
        }
        
        if(isTrained=="<%=Constants.STATUS_VALID%>"){
        	if(typeof(otherTrainingTime)=="undefined"||otherTrainingTime.trim()==""||otherTrainingTime.trim()=="0"){
            	alert("其他培训次数/年必须填写且不能为0 ");
            	return false;
            }
        }

        
        $.ajax({
            url: 'FarmerSkillInfoManage.do',
            data: {
                declareType: declareType,
                status: status,
                declareId: declareId,
                isTrained: isTrained,
                applyType:applyType,
                certificationGrade:certificationGrade,
                certificationDate:certificationDate,
                certificationDepartment:certificationDepartment,
                otherTrainingTime: number,
                hasNoCertification: hasNoCertification,
                hasNewTypeTrainingCertification: hasNewTypeTrainingCertification,
                hasNewTypeCertification: hasNewTypeCertification,
                hasNationalCertification:hasNationalCertification,
                certificationTitle:certificationTitle,

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
        });
    }
}


$(".cert").find("li").on("click",function(){
	var span = $(this).find("span");
    if(span.hasClass("hide-1")){
        span.removeClass("hide-1").addClass("show-1");
        span.next().attr("checked",true)
    }
    else{
        span.removeClass("show-1").addClass("hide-1")
        span.next().attr("checked",false)
    }
    var noCert= $(".qualifications").find(".no-cert");
    var hasCert = $(".qualifications").find(".has-cert");

    if(span.hasClass("no-cert")){

        if(span.hasClass("show-1")){
            if(hasCert.hasClass("show-1")){
                hasCert.removeClass("show-1").addClass("hide-1");
                hasCert.next().attr("checked",false);
            }
        }

    }

    if(span.hasClass("has-cert")){
        if(span.hasClass("show-1")){
            if(noCert.hasClass("show-1")){
                noCert.removeClass("show-1").addClass("hide-1");
                noCert.next().attr("checked",false);
            }
        }
    }
});

function toolBarBack(){
		var declareType = $('#declareType').val();
        window.step --;
        if ($(".g-pb65.fixed").hasClass("show-1")){
            $(".g-pb65.fixed").removeClass("show-1").addClass("hide-1")
            $(".footer-all").attr("style","display : block");
        }else{
            window.location.href='DeclareInfoManage.do?declareType='+declareType;
        }
    }

$("#layer-experience").find("input[type='radio']").click(function(){
    if($(this).is(":checked"))
    {
        //如果参加过其它培训
        if($(this).val() == "1"){
            $(".other-train").show();
        }
        else{
            $(".other-train").hide();
        }
    }
});

$("#layer-certification").find("input[type='radio']").click(function(){
    if($(this).is(":checked"))
    {
        //如果获得证书
        if($(this).val() == "1"){
            $(".cert-option").show();
        }
        else{
            $(".cert-option").hide();
        }
    }
});

var isCert = $('input[name="isCert"]:checked').val();
if(isCert == "<%=Constants.STATUS_VALID%>"){
    $(".cert-option").show();
}
else{
    $(".cert-option").hide();
}

var isTrained = $('input[name="isTrained"]:checked').val();
if(isTrained == "<%=Constants.STATUS_VALID%>"){
    $(".other-train").show();
}
else{
    $(".other-train").hide();
}

$(document).ready(function(){
	//如果未获得证书则
	if($('#hasNoCertification').prop('checked')){
	    if($("#nev-cert").hasClass("hide-1")){
	        $("#nev-cert").removeClass("hide-1").addClass("show-1")
	    }
	}

	//如果未获得培训证书则
	if($('#hasNewTypeTrainingCertification').prop('checked')){
	    if($("#train-cert").hasClass("hide-1")){
	        $("#train-cert").removeClass("hide-1").addClass("show-1")
	    }
	}

	//如果未获得职业证书则
	if($('#hasNationalCertification').prop('checked')){
	    if($("#occu-cert").hasClass("hide-1")){
	        $("#occu-cert").removeClass("hide-1").addClass("show-1")
	    }
	}
});

</script>
</html>