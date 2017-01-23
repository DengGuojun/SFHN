<%@page import="com.lpmas.framework.util.MapKit"%>
<%@page import="com.lpmas.sfhn.declare.config.FarmerJobInfoConfig"%>
<%@page import="com.lpmas.framework.web.ParamKit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.StatusBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.FarmerJobInfoBean" %>
<%@ page import="com.lpmas.sfhn.declare.bean.JobTypeBean" %>
<% 
	FarmerJobInfoBean bean = (FarmerJobInfoBean)request.getAttribute("FarmerJobInfoBean");
	int declareType = ParamKit.getIntParameter(request, "declareType", 0);
	List<JobTypeBean> jobTypeList = (List<JobTypeBean>)request.getAttribute("JobTypeList");
%>
<%@ include file="../../../../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link type="text/css" rel="stylesheet" href="<%=STATIC_URL %>css/style.css">
    <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js" ></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/main.js"></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/common.js"></script>
    <script type="text/javascript" src="<%=STATIC_URL %>js/plugin.js"></script>

    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/my.css">
    <link rel="stylesheet" href="http://static.lpmas.com/lpmas/passport/wap/v0/css/common.css">
	<title>从事工作/单位</title>
	<style>
		.form-s1 li{padding-left:13px;font-size:16px}
	</style>
</head>
<body>
<div class="g-h2 f-tac"><a onclick="toolBarBack()" class="back-a"><i class="back-point"></i></a>从事工作/单位</div>
	<div class="placeHolder"></div>
 	<input type="hidden" name="declareId" id="declareId" value="<%=bean.getDeclareId()%>"/>
	<input type="hidden" name="status" id="status" value="<%=bean.getStatus()%>"/>
	<input type="hidden" name="declareType" id="declareType" value="<%=declareType%>"/>
	<input type="hidden" name="country" id="country" value="中国">
	
	<div class="g-pb65 show-1">
    	<section class="info-table">
    	 <ul class="form-s1">
    	 	<li>
                 <dd><i class="star">*</i>从业工种:</dd>
                <select class="jobType" id="jobType" name="jobType" >
                <%for(JobTypeBean jobTypeBean: jobTypeList){ %>
					<option value="<%=jobTypeBean.getTypeId()%>" <%=jobTypeBean.getTypeId()== bean.getJobType() ? "selected" : ""%>><%=jobTypeBean.getTypeName() %></option>
				<%} %>
                </select>
            </li>
            
            <li>
                 <dd><i class="star">*</i>从事岗位:</dd>
                <input type="hidden" name="originalJobId" id= "originalJobId" value="<%=bean.getJobId()%>"/>
                <select class="job" id="job" name="job" ></select>
            </li>
            
            <li>
                <dd><i class="star">*</i>从业年限:</dd>
                <dt><input maxlength="5" placeholder="年限数" type="number" name="experience" id="experience" value="<%=bean.getExperience() >0 ? bean.getExperience() : ""%>" size="5" maxlength="5"></dt>
            </li>
            
             <li>
             	<dd style="width:150px"><i class="star">*</i><p>个人从事该工种/岗位年收入（万）:</p></dd>
                <dt><input maxlength="20" style="width:70px" type="number" name="income" id="income" placeholder="年收入" value="<%=bean.getIncome() > 0 ? bean.getIncome() : "" %>" size="5" maxlength="5"></dt>
            </li>
            <li id="companytype">
                <dd ><i class="star">*</i>从业单位类别:</dd>
                <dt><input id="companytype-input" value="<%=MapKit.getValueFromMap(bean.getCompanyType(), FarmerJobInfoConfig.JOB_COMPANY_TYPE_MAP) %>"  type="text" disabled /></dt>
                <span><img src="<%=STATIC_URL %>images/icon_go.png" /></span>
            </li>
              <li class="address_add_pca clear">
                <label  class="col-2 left" for="newAddLocation"><i class="star">*</i>工作地点 ：</label>
                <div class="col-10 cell" style="width:200px">
                    <input maxlength="25" type="hidden" name="province" id="jobProvince" autocomplete="off" data-verify="city" value="<%=bean.getJobProvince()%>">
                    <input maxlength="25" type="hidden" name="city" id="jobCity" autocomplete="off" data-verify="city" value="<%=bean.getJobCity()%>">
                    <input maxlength="25" type="hidden" name="region" id="jobRegion" autocomplete="off" data-verify="city" value="<%=bean.getJobRegion()%>">
                    <div class="address_detail" id="addressDetail">
                        <input type="hidden" name="country" placeholder="国家/地区" value="中国" autocomplete="off" readonly="">
                        <input type="text" name="location" id="newAddLocation" placeholder="省/直辖市 - 市 - 区县" autocomplete="off" data-verify="pca" readonly="" value="<%=bean.getJobProvince()%><%=bean.getJobCity()%><%=bean.getJobRegion()%>">
                    </div>
                </div>
               <!--  <i class="col-2 cell"><b>*</b></i> -->
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
    	  </ul>
        </section>
	</div>
	<!-- 从业单位类别 -->
	<div class="g-pb65 hide-1 fixed"  id="layer-companytype" >
	<div class="placeHolder"></div>
	    <ul class="form-s1">
	    <% String companytype = bean.getCompanyType(); %>
	    <%for(StatusBean<String,String> statusBean : FarmerJobInfoConfig.JOB_COMPANY_TYPE_LIST){ %>
			<li>
	            <input type="radio" name="companyType" value="<%=statusBean.getStatus()%>" <%=(statusBean.getStatus().equals(companytype)) ? "id='radio-true' checked" : "id='radio-false'"%>><!--value-->
	            <label for="<%=(statusBean.getStatus().equals(companytype)) ? "radio-true" : "radio-false"%>"><%=statusBean.getValue() %></label>
	        </li>
		<%} %>
	    </ul>
	</div>
	
	<footer class="footer-all" id="base-save-btn">保存</footer>
	<footer class="footer-50">
    <div class="footer-w50l">取消</div>
    <div class="footer-w50r">保存</div></footer>
</body>
<script>
var edu = {};
var political = {};
var nation = {};
var personal = {};
var area= {};
var work={};
var grade = {};
var work2={};
var grade2 = {};
var economic={};
var breed = {};
var companytype = {
	<%for(StatusBean<String,String> statusBean : FarmerJobInfoConfig.JOB_COMPANY_TYPE_LIST){ %>
		'<%=statusBean.getStatus()%>':'<%=statusBean.getValue()%>',
	<%} %>
};
$(document).ready(function() {

	  $("#jobType").on("change",function(){
	      var typeId = $(this).find("option:selected").val();
	      seletCate(typeId,$(this));
	  });
	  $("#jobType").trigger('change');

	  function seletCate(typeId,element){
		  $.ajax({
  	        type: 'get',
  	        url: "/sfhn/JobInfoJsonList.do?typeId="+typeId,
  	        dataType: 'json',
  	        success: function(data){
  	        		var jobs=data.result;
  	        		child ="";
                for(var j=0;j< jobs.length;j++){
              	 	var originalId = element.parent().parent().find(".originalJobId").val();
              	 	if(jobs[j].jobId == originalId){
              	 		child += "<option value='" + jobs[j].jobId  +"' selected>"+ jobs[j].jobName + "</option>"
              	 	}else{
              	 		child += "<option value='" + jobs[j].jobId  +"'>"+ jobs[j].jobName + "</option>"
              	 	}
               }
               $("#job").html(child);
  	        },
  	        error: function(){
  	            return;
  	        }
		});
	  }		  
});

$("#base-save-btn").click(
		function() {
		//获取表单数据
		var declareId = $('#declareId').val();
		var declareType = $('#declareType').val();
		var status = $('#status').val();
		var country = $('#country').val();
		var jobType = $('#jobType').val();
		var jobName = $('#job').val();
		var experience = $('#experience').val();
		var income = $('#income').val();
		var companyType = $('input[name="companyType"]:checked').val();
		var jobProvince = $('#jobProvince').val();
		var jobCity = $('#jobCity').val();
		var jobRegion = $('#jobRegion').val();
		if (jobType == '' || jobName == ''){
			alert("从业工种/岗位必须填写");
			return;
		}
		if (typeof(companyType)=="undefined"){
			alert("从业单位类别必须填写");
			return;
		}
		if (jobProvince == '' || jobRegion == '' || jobCity == ''){
			alert("工作地点必须填写");
			return;
		}
		if (typeof(experience)=="undefined"||experience==''){
			alert("从业年限必须填写");
			return;
		}
		if (typeof(income)=="undefined"||income==''){
			alert("从事岗位年收入必须填写");
			return;
		}
		//保留表单验证
		
		//表单提交
		 $.ajax({
	            url: 'FarmerJobInfoManage.do',
	            data: {
	                declareId: declareId,
	                declareType: declareType,
	                status: status,
	                country: country,
	                jobType: jobType,
	                jobId: jobName,
	                experience: experience,
	                income: income,
	                companyType: companyType,
	                jobProvince: jobProvince,
	                jobCity: jobCity,
	                jobRegion: jobRegion,
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
function toolBarBack(){
	if ($(".g-pb65.fixed").hasClass("show-1")){
		$(".g-pb65.fixed").attr("class","g-pb65 fixed hide-1");
		$(".footer-all").attr("style","display : block");
	}else{
		window.location.href='DeclareInfoManage.do?declareType=<%=declareType%>';
	}
}
</script>
</html>