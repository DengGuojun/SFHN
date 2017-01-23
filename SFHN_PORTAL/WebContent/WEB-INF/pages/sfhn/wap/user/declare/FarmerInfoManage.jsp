<%@page import="com.lpmas.framework.util.MapKit"%>
<%@page import="com.lpmas.sfhn.declare.config.NationalityConfig"%>
<%@page import="com.lpmas.sfhn.declare.config.DeclareImageInfoConfig"%>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@page import="com.lpmas.sfhn.declare.config.FarmerInfoConfig"%>
<%@page import="com.lpmas.framework.bean.StatusBean"%>
<%@page import="com.lpmas.framework.util.DateKit"%>
<%@page import="com.lpmas.sfhn.portal.declare.config.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.lpmas.sfhn.declare.bean.*"%>
<!DOCTYPE html>
<%
	DeclareInfoBean declareInfoBean = (DeclareInfoBean)request.getAttribute("DeclareInfoBean");
	FarmerInfoBean farmerInfoBean = (FarmerInfoBean) request.getAttribute("FarmerInfoBean");
	DeclareImageBean imageBean = (DeclareImageBean) request.getAttribute("DeclareImageBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
%>
<%@ include file="../../../../include/header.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<!-- 
<meta name="msapplication-tap-highlight" content="no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black"> -->
<script src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="<%=STATIC_URL %>js/main.js"></script>
<script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
<script type="text/javascript" src="<%=STATIC_URL %>js/plugin.js"></script>
<link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
<script>
	te$.setMyWapStyle();
</script>
<title>基本信息</title>
</head>
<body>
<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>基本信息</div>
<div class="placeHolder"></div>
	<input type="hidden" name="declareId" id="declareId" value="<%=declareInfoBean.getDeclareId() %>"><!--value-->
	<input type="hidden" name="status" id="status" value="<%=declareInfoBean.getStatus()%>"><!--value-->
	<input type="hidden" name="declareType" id="declareType" value="<%=ParamKit.getIntParameter(request, "declareType", 0) %>"><!--value-->
	<div class="g-pb65 show-1">
		<section class="info-table">
			<ul>
           		<li class="photo-section photo" id="photo" onclick="showPhotoLayer()">
	               <dd><i class="star">*</i>1寸照片:</dd>
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
	               <span class="add-photos">
	               <%if(imageBean!=null&&!imageBean.getImagePath().equals("")){ %>
	               <img id="img_main" src="<%=imageBean.getImagePath() %>" class="upload-img" style="top:0px" />
	               <%}else{ %>
	               <img id="img_main" src="<%=STATIC_URL %>images/icon_plus.png" class="plus-img" style="top:44px !important" />
	               <%} %>
	               </span>
				   <input type="hidden" id="imagePath" name="imagePath" value="<%=imageBean.getImagePath()%>"/>
				   <input type="hidden" id="imageType" name="imageType" value="<%=DeclareImageInfoConfig.IMG_TYPE_ONE_INCH%>"/>
           		</li>
           		
           		<li>
		            <dd><i class="star">*</i>姓名:</dd>
		            <dt><input maxlength="25" placeholder="姓名" type="text" id="userName" name="userName" value="<%=farmerInfoBean.getUserName() %>" maxlength="25" /></dt>
           		</li>
           		<li id="sex">
	               <dd><i class="star">*</i>性别:<span></span></dd>
	               <input id="sex-input" value="<%=MapKit.getValueFromMap(farmerInfoBean.getUserGender(), GenderConfig.GENDER_MAP) %>" type="text" disabled />
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
          		 </li>
           		<li style="position: relative">
	               <dd><i class="star">*</i>出生年月:</dd>
	               <input style="width:136px" type="date" id="userBirthday" name="userBirthday" value="<%=farmerInfoBean.getUserBirthday() !=null ? DateKit.formatDate(farmerInfoBean.getUserBirthday(), DateKit.DEFAULT_DATE_FORMAT):"" %>"  autocomplete="off"/>
          		   <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
          		</li>
          		
          		<li id="nation">
	               <dd><i class="star">*</i>民族:<span class="star"></span></dd>
	               <dt><input maxlength="25" id="nation-input" value="<%=MapKit.getValueFromMap(farmerInfoBean.getNationality(), NationalityConfig.NATIONALITY_MAP) %>" type="text" disabled /></dt>
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
          		</li>
           		
           		<li id="edu">
	               <dd><i class="star">*</i>文化程度:<span class="star"></span></dd>
	               <input maxlength="25" id="edu-input" value="<%=MapKit.getValueFromMap(farmerInfoBean.getEducation(), FarmerInfoConfig.EDUCATION_LEVEL_MAP) %>" type="text" disabled />
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           		</li>
           		
           		<%-- <li>
	               <dd><i class='nostart'></i>专业:<span class="star"></span></dd>
	               <dt><input maxlength="25" placeholder="专业" type="text" id="major" name="major" value="<%=farmerInfoBean.getMajor() %>" maxlength="25" /></dt>
           		</li> --%>
           		
           		<li id="political">
	               <dd><i class="star">*</i>政治面貌:<span class="star"></span></dd>
	               <dt><input maxlength="25" id="political-input" value="<%=MapKit.getValueFromMap(farmerInfoBean.getPoliticalStatus(), FarmerInfoConfig.POLITICAL_STATUS_MAP) %>"  type="text" disabled /></dt>
	               <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           		</li>
           		
           		<%-- <li id="personal">
	               <dd><i class="star">*</i>人员类别:<span class="star"></span></dd>
	               <input maxlength="25" id="personal-input" value="<%=MapKit.getValueFromMap(farmerInfoBean.getFarmerType(), FarmerInfoConfig.PERSONNEL_CATEGORY_MAP) %>" type="text" disabled />
	               <span ><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
           		</li> --%>
          		
          		<li>
	               <dd><i class="star">*</i>身份证号:<span class="star"></span></dd>
	               <dt><input maxlength="25" placeholder="身份证号" type="text" id="identityNumber" name="identityNumber" value="<%=farmerInfoBean.getIdentityNumber() %>" maxlength="18" /></dt>
           		</li>
			</ul>
		</section>
	</div>

	<!-- 上传图片 -->
	<div class="g-pb65 hide-1 fixed picture_pop" id="layer-photo" >
	    <div style="text-align: center;margin-top:62px; " class="info-table">
	        <span  class="add-photos-big" style="position: relative">
	        
	         <%if(imageBean!=null&&!imageBean.getImagePath().equals("")){ %>
	               <img id="img_preview" src="<%=imageBean.getImagePath() %>" width="86" height="129" />
	               <%}else{ %>
	               <img id="img_preview" src="<%=STATIC_URL %>images/icon_plus.png" />
	         <%} %>
	        <input type="file" id="file" name="file" accept="image/*" onchange="up();"  /></span>
	        <p style="margin-top: 20px">请上传本人1寸相片</p>
	        <p>大小不超过1M</p>
	        <p>格式为JPG或PNG</p>
	    </div>
	</div>
	<!-- 性别 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-sex" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    	<% int userGender = farmerInfoBean.getUserGender(); %>
	    	<%for(StatusBean<Integer,String> statusBean : GenderConfig.GENDER_LIST){ %>
			<li>
	            <input type="radio" name="userGender" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus()==userGender) ? "id='radio-true' checked" : "id='radio-false'"%> ><!--value-->
	            <label for="<%=(statusBean.getStatus()==userGender) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
			<%} %>
	    </ul>
	</div>
	
	<!-- 文化程度 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-edu" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String education = farmerInfoBean.getEducation(); %>
	    <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.EDUCATION_LEVEL_LIST){ %>
			<li>
	            <input type="radio" name="education" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(education)) ? "id='radio-true' checked" : "id='radio-false'"%> ><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(education)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div>
	
	<!-- 人员类型 -->
	<%-- <div class="g-pb65 hide-1 fixed pop"  id="layer-personal" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String farmerType = farmerInfoBean.getFarmerType(); %>
	    <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.PERSONNEL_CATEGORY_LIST){ %>
			<li>
	            <input type="radio" name="farmerType" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(farmerType)) ? "id='radio-true' checked" : "id='radio-false'"%>><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(farmerType)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div> --%>
	<!-- 民族 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-nation" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String nationality = farmerInfoBean.getNationality(); %>
	     <%for(StatusBean<String,String> statusBean : NationalityConfig.NATIONALITY_LIST){ %>
			<li>
	            <input type="radio" name="nationality" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(nationality)) ? "id='radio-true' checked" : "id='radio-false'"%>><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(nationality)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div>
	<!-- 政治面貌 -->
	<div class="g-pb65 hide-1 fixed pop"  id="layer-political" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String politicalStatus = farmerInfoBean.getPoliticalStatus(); %>
	    <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.POLITICAL_STATUS_LIST){ %>
			<li>
	            <input type="radio" name="politicalStatus" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(politicalStatus)) ? "id='radio-true' checked" : "id='radio-false'"%>><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(politicalStatus)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div>
	
	<footer class="footer-all" id="base-save-btn">保存</footer>
	<footer class="footer-50">
	    <div class="footer-w50r" onclick="hideFooter()" style="width:100%">保存</div>
    </footer>
    <div id="loading" class="popup" style="display: block; ">
    	<div class="loadingbox"><span class="loadingbtn"></span></div>
	</div>
</body>
<script type="text/javascript">
var edu = {
		<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.EDUCATION_LEVEL_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var political = {
		<%for(StatusBean<String,String> statusBean : FarmerInfoConfig.POLITICAL_STATUS_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var nation = {
		<%for(StatusBean<String,String> statusBean : NationalityConfig.NATIONALITY_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %>
};
var personal = {
		<%-- <%for(StatusBean<String,String> statusBean : FarmerInfoConfig.PERSONNEL_CATEGORY_LIST){ %>
			'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
		<%} %> --%>
};
var area= {};
var economic={};
var work = {};
var grade = {};
var work2 = {};
var grade2 = {};
var companytype = {};
var breed = {};
$(document).ready(function() {
	$("#base-save-btn").click(
			function() {
			//获取表单数据
			var declareId = $('#declareId').val();
			var declareType = $('#declareType').val();
			var status = $('#status').val();
			var imagePath = $('#imagePath').val();
			var imageType = $('#imageType').val();
			var userName = $('#userName').val();
			var userGender = $('input[name="userGender"]:checked').val();
			var userBirthday = $('#userBirthday').val();
			var nationality = $('input[name="nationality"]:checked').val();
			var education = $('input[name="education"]:checked').val();			
			var politicalStatus = $('input[name="politicalStatus"]:checked').val();
			var identityNumber = $('#identityNumber').val();
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			if (imagePath == ''){
				alert("图片必须上传");
				return;
			}
			if (userName == ''){
				alert("姓名必须填写");
				return;
			}
			if (typeof(userGender)=="undefined"){
				alert("性别必须填写");
				return;
			}
			if (userBirthday == ''){
				alert("出生日期必须填写");
				return;
			}
			if (typeof(nationality)=="undefined"){
				alert("民族必须填写");
				return;
			}
			if (typeof(education)=="undefined"){
				alert("文化程度必须填写");
				return;
			}
			if (typeof(politicalStatus)=="undefined"){
				alert("政治面貌必须填写");
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
			//保留表单验证
			
			//表单提交
			 $.ajax({
		            url: 'FarmerInfoManage.do',
		            data: {
		                declareId: declareId,
		                declareType: declareType,
		                status: status,
		                imagePath: imagePath,
		                imageType: imageType,
		                userName: userName,
		                userGender: userGender,
		                userBirthday: userBirthday,
		                nationality: nationality,
		                education: education,		          
		                politicalStatus: politicalStatus,
		                identityNumber: identityNumber
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
		});
	
	$('#loading').hide();
});

function up() {
	$('#loading').show();
    var file_data = $('#file')[0].files; // for multiple files
    //验证文件大小，文件类型
    var file = file_data[0];
    var size = file.size;
    var maxSize = <%=DeclareImagePortalConfig.MAX_SIZE%>;
    if(size>maxSize){
    	alert("文件大小超过限制");
    	return;
    }
    var name = (file.name).toLowerCase();
    if(!(name.lastIndexOf('jpg')>0||name.lastIndexOf('jpeg')>0||name.lastIndexOf('png')>0)){
    	alert("图片类型错误，请上传JPG或者PNG图片");
    	return;
    }
    //文件压缩
    var canvas = document.createElement('canvas');
    var ctx = canvas.getContext('2d');
    var scale = 0;
    var path = window.URL.createObjectURL(file);
    var imgData = "";
    var imgType = file.type;
    //判定压缩比
    if(size<=1024*1024){
    	scale = 1;
    }else{
    	scale = 0.5;
    }
    
    var image = new Image();
    image.onload = function() {
        if (image.width != canvas.width) {
            canvas.width = image.width;
        }
        if (image.height != canvas.height) {
            canvas.height = image.height;
        }
        ctx.clearRect(image,0, 0, canvas.width, canvas.height);
        ctx.drawImage(image, 0, 0);
        imgData = canvas.toDataURL(imgType,scale);
        window.URL.revokeObjectURL(path);
        
      //提交
        for (var i = 0; i < file_data.length; i++) {
            $.ajax({
                url: '/sfhn/DeclareImageManage.do?declareType=<%=ParamKit.getIntParameter(request, "declareType", 0) %>',
                data: {
                	imgData:imgData,
                	imgType:imgType
                },
                type: 'POST',
                success: function (data) {
                    console.log(data);
                    if(data.code==200){
                        var url=data.message;
                        var img_main = $('#img_main');
                        var img_preview = $('#img_preview');
                        $('#imagePath').val(url);
                        
                        img_preview.attr("src",url);
                        img_preview.attr("width","86");
                        img_preview.attr("height","129");
                        
                        img_main.attr("src",url);
                        img_main.removeClass("plus-img");
                        img_main.addClass("upload-img");
                        img_main.removeAttr('style');
                        img_main.attr('style',"top:0px");
                    }else{
                    	alert(data.message);
                    	$('#loading').hide();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log(errorThrown);
                    alert(errorThrown);
                    $('#loading').hide();
                }
            });
        }
    }
    image.src=path;
    //文件压缩结束
    
    $('#img_preview')[0].onload = function() {
    	$('#loading').hide();
    }
}

function showPhotoLayer(){
	$(".footer-50").show();
	$("#layer-photo").show();
}
function hideFooter(){
	$(".footer-50").hide();
	$("#layer-photo").hide();
	$(".footer-all").show();
}
function toolBarBack(){
	var status = $(".picture_pop").css("display");
	if ($(".pop").hasClass("show-1")){
		$(".pop").attr("class","g-pb65 fixed hide-1 pop");
		$(".footer-all").attr("style","display : block");
	}else if (status == 'block'){
		$(".picture_pop").attr("style","display : none");
		$(".footer-50").attr("style","display : none");
		$(".footer-all").attr("style","display : block");
	}
	else{
		window.location.href='DeclareInfoManage.do?declareType=<%=declareType%>';
	}
}
</script>
</html>