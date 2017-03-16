<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@page import="com.lpmas.constant.user.GenderConfig"%>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
	TeacherInfoBean bean = (TeacherInfoBean)request.getAttribute("TeacherInfo");
	List<MajorTypeBean> majorTypeList = (List<MajorTypeBean>)request.getAttribute("MajorTypeList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	String userName = (String)request.getAttribute("UserName");
	MajorInfoBean majorInfoBean = (MajorInfoBean)request.getAttribute("MajorInfoBean");
	Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
	GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
	String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String teacherProvince = (String)request.getAttribute("TeacherProvince");
	String teacherCity = (String)request.getAttribute("TeacherCity");
	String teacherRegion = (String)request.getAttribute("TeacherRegion");
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
        <script type="text/javascript" src="<%=STATIC_URL %>js/admin_common.js"></script>
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a><%if(bean.getTeacherId() == 0){ %>新增教师信息<%}else{ %>编辑教师信息<%} %></span>
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
                    <form class="form-box col-xs-8" id="formData" method="post"  onsubmit="javascript:return checkForm('formData');">
                    		<input type="hidden" name="userId" id="userId" value="<%=bean.getUserId() %>"/>
                    		<input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
                    		<input type="hidden" name="syncStatus" id="syncStatus" value="<%=bean.getSyncStatus() %>"/>
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">教师姓名</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="teacherName"  value="<%=bean.getTeacherName() %>" checkStr="教师姓名;txt;true;;10">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">性别</div>
                                <div class="col-xs-10">
                                    <select name="teacherGender" class="form-control">
                                        <option></option>
					                     <%for(StatusBean<Integer,String> statusBean  : GenderConfig.GENDER_LIST){ %>
											<option value="<%=statusBean.getStatus()%>"  <%=statusBean.getStatus().equals(bean.getTeacherGender()) ? "selected" : ""%>><%=statusBean.getValue() %></option>
										<%} %>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">身份证号</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control"  name="identityNumber"  value="<%=bean.getIdentityNumber() %>" checkStr="身份证;txt;true;;30">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">年龄</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="teacherAge"  value="<%=bean.getTeacherAge() >0 ? bean.getTeacherAge() : "" %>" checkStr="年龄;num;false;;10">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">手机号</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="teacherMobile"  value="<%=bean.getTeacherMobile() %>" checkStr="手机号;num;true;;20" <%=StringKit.isValid(bean.getTeacherMobile()) ? "readonly": "" %>>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">区域</div>
                                <input type="hidden" id="province"  name="province" value="<%=fixProvince %>"/>
                    				<input type="hidden" id="city"  name="city" value="<%=StringKit.isValid(bean.getCity()) ? bean.getCity() : fixCity %>"/>
           		    				<input type="hidden" id="region" name="region" value="<%=StringKit.isValid(bean.getCity())  ? bean.getRegion() : fixRegion %>"/>
                                <div class="col-xs-10">
                                    <select style="width: 32%; float: left;"  class="form-control" name="selectProvince" id="selectProvince" onchange="showCity()">
                                    </select>
                                    <select  style="width: 32%; float: left;margin-left: 2%;"  class="form-control" name="selectCity" id="selectCity" onchange="showRegion()">
                                    </select>
                                    <select style="width: 32%; float: left; margin-left: 2%;" class="form-control" name="selectRegion" id="selectRegion" onchange="setRegion()">
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">单位</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="corporateName"  value="<%=bean.getCorporateName() %>" checkStr="单位;txt;false;;50">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">住址</div>
                                <div class="col-xs-10">
                                     <input type="text" class="form-control" name="address"  value="<%=bean.getAddress() %>" checkStr="地址;txt;false;;50">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">专业</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <select style="width: 49%; float: left;" class="form-control" id="majorType" name="majorTypeId" onchange="typeSelectionChange();">
                                                 <%for(MajorTypeBean typeBean  : majorTypeList){ %>
												<option value="<%=typeBean.getMajorId()%>"  <%=typeBean.getMajorId() == majorInfoBean.getTypeId() ? "selected" : ""%>><%=typeBean.getMajorName() %></option>
												<%} %>
                                            </select>
                                            <select style="width: 49%; float: left; margin-left: 2%;"  class="form-control" id="majorInfo" name="majorId" checkStr="专业;txt;true;;200">
                                                <option value=""></option>
                                            </select>
                                            <input type="hidden" id="majorInfoId" value="<%=bean.getMajorId() %>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">主讲课程</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <input type="text" class="form-control" name="mainCourse"  value="<%=bean.getMainCourse() %>" checkStr="珠江课程;txt;false;;50">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-center btn-wrp">
                            <button type="submit" class="btn btn-success btn-mini">提交</button>
                        </div>
                    </form>
                    
                </div>
            </div>
        </div>
    </div>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
	<script type="text/javascript">
        $(function(){
            mScroller();
        })
    </script>
</body>
<script>
$(document).ready(function() {
	typeSelectionChange();
	showProvince();
});
function typeSelectionChange(){
	var majorInfoId = $("#majorInfoId").val();
	var majorTypeId=$("#majorType").val();
	if(majorTypeId != null){
		var params={  
		        'majorTypeId':majorTypeId  
	    };  
	    $.ajax({
	        type: 'get',
	        url: "/sfhn/admin/MajorInfoJsonList.do",
	        data: params,
	        dataType: 'json',
	        success: function(data){
		      	var sel2 = $("#majorInfo");  
	      		sel2.empty();  
		      	if(data==null) {
	      		 	sel2.append("<option value =''></option>");
	          	}else {
	          		var items=data.result;
		    	    		if(items!=null) {
		    	    	  	sel2.append("<option value =''></option>");
		        	  	for(var i =0;i<items.length;i++) {
		          		var item=items[i];
		          		if(item.majorId==majorInfoId){
		          			sel2.append("<option value = '"+item.majorId+"' selected>"+item.majorName+"</option>");
		          		}else{
		            			sel2.append("<option value = '"+item.majorId+"'>"+item.majorName+"</option>");
		          		}
		           	};
		        } else{
		       		sel2.empty();  
		          }
	          	}
		      	
	        },
	        error: function(){
	            return;
	        }
	    });
	}
}

function showProvince(){
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/ProvinceList.action?jsoncallback=provinceData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function provinceData(data){
	var sel = $("#selectProvince");  
	sel.empty();  
	sel.append("<option value = ''></option>");
	var items=data.content.provinceList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixProvince = "<%=fixProvince%>";
	      	var teacherProvince = "<%=teacherProvince%>";
	      	if(fixProvince == item.provinceName){
	      		sel.append("<option value = '"+item.provinceId+"' selected>"+item.provinceName+"</option>");
	      	}else if(teacherProvince == item.provinceName){
	      		sel.append("<option value = '"+item.provinceId+"' selected>"+item.provinceName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.provinceId+"'>"+item.provinceName+"</option>");
	      	}
	      	$("#selectProvince").attr("disabled","disabled");
	    };
	    showCity();
    } else{
   		sel.empty();  
    }
}

function showCity(){
	var provinceId = $("#selectProvince").val();
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/CityList.action?provinceId="+provinceId+"&jsoncallback=cityData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function cityData(data){
	var isFix;
	var isModify;
	var sel = $("#selectCity");  
	sel.empty();  
	sel.append("<option value = ''>全部</option>");
	var items=data.content.cityList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixCity = "<%=fixCity%>";
	      	var teacherCity = "<%=teacherCity%>";
	      	if(fixCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      		isFix = true;
	      	}else if(teacherCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      		isModify = true;
	      	}else{
	      		sel.append("<option value = '"+item.cityId+"'>"+item.cityName+"</option>");
	      	}
	    };
	    if(isFix){
	    		$("#selectCity").attr("disabled","disabled");
	    		showRegion();
	    }
	    if(isModify){
    		showRegion();
        }
    } else{
   		sel.empty();  
    }
}

function showRegion(){
	var text = $("#selectCity  option:selected").text();
	if(text == "全部"){
		$("#city").val("");
	}else{
		$("#city").val(text);
	} 
	var cityId = $("#selectCity").val();
	$.ajax({
        type: 'POST',
        dataType:'jsonp',
        url: "http://www.lpmas.com/m/RegionList.action?cityId="+cityId+"&jsoncallback=regionData",
        success: function(data){
        },
        error: function(){
            return;
        }
    });
} 

function regionData(data){
	var isFix;
	var sel = $("#selectRegion");  
	sel.empty();  
	sel.append("<option value = ''>全部</option>");
	var items=data.content.regionList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixRegion = "<%=fixRegion%>";
	      	var teacherRegion = "<%=teacherRegion%>";
	      	if(fixRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      		isFix = true;
	      	}else if(teacherRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.regionId+"'>"+item.regionName+"</option>");
	      	}
	    };
	    if(isFix){
	    		$("#selectRegion").attr("disabled","disabled");
	    }
    } else{
   		sel.empty();  
    }
}

function setRegion(){
	var text = $("#selectRegion  option:selected").text();
	if(text == "全部"){
		$("#region").val("");
	}else{
		$("#region").val(text);
	} 
}
</script>
</html>