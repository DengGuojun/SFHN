<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.util.*" %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.*" %>
<%@ page import="com.lpmas.sfhn.declare.config.*" %>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%
List<IndustryTypeBean> industryTypeList = (List<IndustryTypeBean>)request.getAttribute("IndustryTypeList");
String originalIndustryName = (String)request.getAttribute("OriginalIndustryName");
DeclareReportBean declareReportBean = (DeclareReportBean)request.getAttribute("DeclareReportBean");
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
	<script type="text/javascript" src="<%=STATIC_URL %>js/main.js"></script>
	<script type="text/javascript" src="<%=STATIC_URL %>js/plugin.js"></script>

    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/my.css">
    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/common.css">
	<title>新型职业农民登记</title>
</head>
<body class="body-mg">
<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>新型职业农民登记</div>
	<div class="placeHolder"></div>
	  <input type="hidden" name="status" id="status" value="<%=declareReportBean.getStatus()%>"/>
	  <section class="info-table mb-55">
       <ul>
          <li>
		        <dd><i class="star">*</i>姓名:</dd>
		        <dt><input maxlength="25" placeholder="姓名" type="text" id="userName" name="userName" value="<%=declareReportBean.getUserName() %>" /></dt>
         </li>
          <li id="sex">
	               <dd><i class="star">*</i>性别:<span></span></dd>
	               <input id="sex-input" value="<%=MapKit.getValueFromMap(declareReportBean.getUserGender(), GenderConfig.GENDER_MAP) %>" type="text" disabled />
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
          </li>
          <li>
	      <dd><i class="star">*</i>手机号码:</dd>
	      <dt><input maxlength="20" placeholder="手机号码" type="number" name="userMobile" id="userMobile" value="<%=declareReportBean.getUserMobile() %>" /></dt>
	      </li>
          <li>
	               <dd><i class="star">*</i>身份证号:<span class="star"></span></dd>
	               <dt><input maxlength="25" placeholder="身份证号" type="text" id="identityNumber" name="identityNumber" value="<%=declareReportBean.getIdentityNumber() %>" maxlength="18" /></dt>
           </li>
           <li id="industry" onclick="showIndustryLayer()">
               <dd>主体产业:<i class="star">*</i></dd>
               <input id="industry-input" value="<%=originalIndustryName!=null ? originalIndustryName : "" %>" type="text" disabled />
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
           <li>
               <dd><i class="star">*</i>产业规模:</dd>
               <dt><input maxlength="25" placeholder="产业规模" type="text" id="industrySize1" name="industrySize1" value="<%=declareReportBean.getIndustryScale1() > 0 ? declareReportBean.getIndustryScale1() : "" %>"/></dt>
           </li>
           <li id="breed">
               <dd><i class="star">*</i>培育类型:<span></span></dd>
               <input id="breed-input" value="<%=MapKit.getValueFromMap(declareReportBean.getDeclareType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %>" type="text" disabled="">
               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           </li>
           <li class="address_add_pca clear">
                <label class="col-2 left" for="newAddLocation"><i class="star">*</i>户籍所在地：</label>
                <div class="col-10 cell" style="width:180px">
                    <input maxlength="25" type="hidden" name="province" id="province" autocomplete="off" data-verify="city" value="<%=declareReportBean.getProvince()%>">
                    <input maxlength="25"  type="hidden" name="city" id="city" autocomplete="off" data-verify="city" value="<%=declareReportBean.getCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="region" autocomplete="off" data-verify="city" value="<%=declareReportBean.getRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text"  name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=declareReportBean.getProvince()%><%=declareReportBean.getCity()%><%=declareReportBean.getRegion()%>">
                    </div>
                </div>
                <!-- <i class="col-2 cell"><b>*</b></i>-->
            </li>
            <li class="pca_select choice_address popup-wrp" id="pcaSelect">
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
       </ul>
       <div class="popup-mask js-mask">
       </div>
    </section>    
    <!-- 性别 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-sex" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    	<% int userGender = declareReportBean.getUserGender(); %>
	    	<%for(StatusBean<Integer,String> statusBean : GenderConfig.GENDER_LIST){ %>
			<li>
	            <input type="radio" name="userGender" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()==userGender) ? "id='radio-true' checked" : "id='radio-false'"%> ><!--value-->
	            <label for="<%=(statusBean.getStatus()==userGender) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
			<%} %>
	    </ul>
	</div>
	<!-- 培育类型 -->
    <div class="g-pb65 hide-1 fixed pop" id="layer-breed">
        <div class="placeHolder"></div>
        <ul class="form-s1">
            <% Integer declareType = declareReportBean.getDeclareType(); %>
	    	<%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
			<li>
	            <input type="radio" name="breedSize" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()==declareType) ? "id='radio-true' checked" : "id='radio-false'"%> ><!--value-->
	            <label for="<%=(statusBean.getStatus()==userGender) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
			<%} %>
        </ul>
    </div>
    
<!-- 主体产业 -->
<div class="g-pb65 hide-1 fixed pop2" id="layer-industry">
<div class="placeHolder"></div>
    <ul class="form-s1">
         <li>
            <em><span>*</span>主体产业:</em>
            <select class="industryType" id="industryTypeId1" name="industryTypeId1" >
            <%for(IndustryTypeBean industryTypeBean: industryTypeList){ %>
				<option value="<%=industryTypeBean.getTypeId()%>" <%=industryTypeBean.getTypeId() == declareReportBean.getIndustryTypeId1() ? "selected" : ""%>><%=industryTypeBean.getTypeName() %></option>
			<%} %>
            </select>
        </li>
        <li>
            <em><span>*</span>产业名称:</em>
            <input type="hidden" class="originalIndustryId" value="<%=declareReportBean.getIndustryId1() %>">
            <select class="industry" id="industryId1" name="industryId1" ></select>
        </li>
        <li>
            <em><span>*</span>单位:</em>
            <select class="industryUnit" id="industryUnit1" name="industryTypeUnit1" >
				<option value="亩" <%=declareReportBean.getIndustryUnit1().equals("亩") ? "selected" : ""%>>亩</option>
				<option value="只/羽/头/箱" <%=declareReportBean.getIndustryUnit1().equals("只/羽/头/箱") ? "selected" : ""%>>只/羽/头/箱</option>
				<option value="公顷" <%=declareReportBean.getIndustryUnit1().equals("公顷") ? "selected" : ""%>>公顷</option>
            </select>
        </li>
    </ul>
</div>
<footer class="footer-all" id="produce-save-btn">保存</footer>
<footer class="footer-50">
<div class="footer-w50r" onclick="hideFooter()" style="width:100%">保存</div></footer>
<script>
var edu = {};
var political = {};
var nation = {};
var personal = {};
var work = {};
var grade = {};
var work2 = {};
var grade2 = {};
var companytype = {};
var area= {};
var economic={};
var breed={
    <%for(StatusBean<Integer,String> statusBean : DeclareInfoConfig.DECLARE_TYPE_LIST){ %>
	'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
	<%} %>
};
$(document).ready(function() {
	 var child = "";
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
   /* var re = /^[0-9]+[0-9]*]*$/;*/
    var userName = $('#userName').val();
    var userGender = $('input[name="userGender"]:checked').val();
    var userMobile = $('#userMobile').val();
    var identityNumber = $('#identityNumber').val();
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var industryTypeId1 = $('#industryTypeId1').val();
    var industryId1 = $('#industryId1').val();
    var industrySize1 = $('#industrySize1').val();
    var industryUnit1 = $('#industryUnit1').val();
    var breedSize = $('input[name="breedSize"]:checked').val();
    var province = $('#province').val();
    var city = $('#city').val();
    var region = $('#region').val();
    var status =$('#status').val();

        if (userName == ''){
            alert("姓名必须填写");
            return;
        }
        if (typeof(userGender)=="undefined"){
            alert("性别必须填写");
            return;
        }
        if (userMobile == ''){
            alert("手机号必须填写");
            return;
        }
        if (identityNumber == ''){
            alert("身份证号必须填写");
            return;
        }
        if(reg.test(identityNumber) === false)
        {
            alert("身份证输入不合法");
            return ;
        }
        if (industryTypeId1 == '' || industryId1 == '' ){
			alert("产业必须填写");
			return;
		}
        if (industrySize1 == '' || industrySize1 == '0'){
            alert("产业规模必须填写且不能为0");
            return;
        }
       if (typeof(breedSize)=="undefined"){
           alert("培育类型必须填写");
           return;
        }       
        if (province == '' || city == '' || region == ''){
			alert("户籍所在地必须填写");
			return;
		}
        $.ajax({
            url: 'DeclarePortalIndex.do',
            data: {
                declareType: breedSize,
                status: status,
                userName: userName,
                userGender: userGender,
                userMobile: userMobile,
                identityNumber: identityNumber,
                industryTypeId1: industryTypeId1,
                industryId1: industryId1,
                industryScale1: industrySize1,
                industryUnit1: industryUnit1,
                province: province,
                city: city,
                region: region,
            },
            type: 'POST',
            success: function (json) {
                console.log(json);
                json = ajaxAuthCheck(json);
                if (json.code == '1') {
                    window.location = 'TrainingClassInfoList.do?declareType='+json.message
                }
                else if (json.code == '0') {
                    alert(json.message);
                }
            }
        })

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
		$(".pop").attr("class","g-pb65 hide-1 fixed pop");
		$(".footer-all").attr("style","display : block"); 
	}
	else{
		window.location.href='DeclarePortalIndex.do';
	}
}
$('#pcaSelectRegion').on('click', function(){ $('.js-mask').hide() })
$('#addressDetail').on('click', function(){ $('.js-mask').show(); })
$('.cancel').on('click',function(){ $('.js-mask').hide(); })
</script>
</body>
</html>