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
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%
	List<TeacherInfoBean> list = (List<TeacherInfoBean>)request.getAttribute("TeacherInfoList");
	Map<Integer,String> majorInfoMap = (Map<Integer,String>)request.getAttribute("MajorInfoMap");
	Map<Integer,String> majorTypeMap = (Map<Integer,String>)request.getAttribute("MajorTypeMap");
	List<MajorTypeBean> majorTypeList = (List<MajorTypeBean>)request.getAttribute("MajorTypeList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    String fixProvince = (String)request.getAttribute("FixProvince");
	String fixCity = (String)request.getAttribute("FixCity");
	String fixRegion = (String)request.getAttribute("FixRegion");
	String queryCity = ParamKit.getParameter(request, "queryCity", "");
	String queryRegion = ParamKit.getParameter(request, "queryRegion", "");
	String queryProvince = ParamKit.getParameter(request, "queryProvince", "");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>师资信息库</span>
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
			       <form id="teacherForm">
                    <div class="search-box">
                    <input type="hidden" id="queryProvince"  name="queryProvince" value="<%=queryProvince !=null ? queryProvince : "" %>"/>
                    <input type="hidden" id="queryCity"  name="queryCity" value="<%=queryCity !=null ? queryCity : "" %>"/>
           		    <input type="hidden" id="queryRegion" name="queryRegion" value="<%=queryRegion !=null ? queryRegion : "" %>"/>
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-3">区域:</div>
                                    <div class="col-xs-3">
                                         <select class="form-control" name="selectProvince" id="selectProvince" onchange="showCity()">
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                         <select class="form-control" name="selectCity" id="selectCity" onchange="showRegion()">
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <select class="form-control" name="selectRegion" id="selectRegion" onchange="setRegion()">
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div> 
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-3">专业:</div>
                                    <div class="col-xs-3">
                                         <select class="form-control" id="majorType" name="majorTypeId" onchange="typeSelectionChange();">
                                                  <option value=""></option>
                                                 <%for(MajorTypeBean typeBean  : majorTypeList){ %>
												<option value="<%=typeBean.getMajorId()%>"  <%=typeBean.getMajorId() == ParamKit.getIntParameter(request, "majorTypeId", 0) ? "selected" : ""%>><%=typeBean.getMajorName() %></option>
												<%} %>
                                         </select>
                                    </div>
                                    <div class="col-xs-3">
                                         <select  class="form-control" id="majorInfo" name="majorId" checkStr="专业;txt;true;;200">
                                                <option value=""></option>
                                         </select>
                                         <input type="hidden" id="majorInfoId" value="<%=ParamKit.getIntParameter(request, "majorId", 0) %>"/>
                                    </div>
                                </div>
                            </div>
                        </div>     
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                		<div class="col-xs-3">
                        					<input type="text" placeholder="姓名" class="form-control" name="teacherName" id="teacherName" value="<%=ParamKit.getParameter(request, "teacherName", "")%>" >
	                        			</div>
	                        			<div class="col-xs-3">
	                        				<input type="text" placeholder="身份证" class="form-control"  name="identityNumber" id="identityNumber" value="<%=ParamKit.getParameter(request, "identityNumber", "")%>" />
	                        			</div>
	                        			<div class="col-xs-3">
	                        				<input type="text" placeholder="手机号" class="form-control" name="teacherMobile" id="teacherMobile" value="<%=ParamKit.getParameter(request, "teacherMobile", "")%>" >
	                        			</div>
	                        			<div class="col-xs-3">
	                        				<input type="text" placeholder="主讲课程" class="form-control" name="mainCourse" id="mainCourse" value="<%=ParamKit.getParameter(request, "mainCourse", "")%>" >
	                        			</div>
                                </div>
                            </div>
                        </div>  
                       <div class="col-xs-9 col-xs-offset-3">
	                            <div class="form-horizontal">
	                            	<div class="form-group">
	                            		<div class="col-xs-4">
	                            			<button type="submit" class="btn btn-success btn-block">查询</button>
	                            		</div>
                                        <div class="col-xs-4">
                                            <button type="button" class="btn btn-success btn-block" onclick="javascript:location.href='TeacherInfoManage.do'">新增师资信息</button>
                                        </div>
                                        <div class="col-xs-4">
                                        <a class="upload-wrp" ><input type="file" id="file" name="file" value="上传" class="form-control" onchange="submitSheet();"  ><label for="file">导入师资</label></a>
                                        </div>
	                            	</div>
	                            </div> 
                            </div>
                        </div>
                    </div>
                </form>
                 <section class="section-wrp section-scroll">             
                        <table class="table table-base table-odd">
                            <tr>
                                 <th>姓名</th>
                                 <th>类型</th>
                                 <th>专业</th>
                                 <th>所属单位</th>
                                 <th>主讲课程</th>
                                 <th>操作</th>
                            </tr>
                            <%for(TeacherInfoBean bean : list) {%>
                            <tr>
                                <td><%=bean.getTeacherName() %></td>
                                <td><%=majorTypeMap.get(bean.getTeacherId()) %></td>
                                <td><%=majorInfoMap.get(bean.getTeacherId()) %></td>
                                <td><%=bean.getCorporateName() %></td>
                                <td><%=bean.getMainCourse() %></td>
                                <td><a onclick="javascript:location.href='TeacherInfoManage.do?teacherId=<%=bean.getTeacherId()%>'" class="ml-10">编辑</a><a onclick="javascript:location.href='TeacherInfoView.do?teacherId=<%=bean.getTeacherId()%>'" class="ml-10">查看详情</a></td>
                            </tr>
                            <%} %>
                        </table>            
                   </section>
                    <!-- 分页栏 -->
	               <%@ include file="../../../include/web/page.jsp"%>
                </div>
            </div>
        </div>
      </div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script>
mScroller();
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
	      	if(fixProvince == item.provinceName){
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
	var sel = $("#selectCity");  
	sel.empty();  
	sel.append("<option value = ''>全部</option>");
	var items=data.content.cityList;
    	if(items != null) {
	   	for(var i =0;i<items.length;i++) {
	      	var item=items[i];
	      	var fixCity = "<%=fixCity%>";
	      	var queryCity = "<%=queryCity%>";
	      	if(fixCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      		isFix = true;
	      	}else if(queryCity == item.cityName){
	      		sel.append("<option value = '"+item.cityId+"' selected>"+item.cityName+"</option>");
	      	}else{
	      		sel.append("<option value = '"+item.cityId+"'>"+item.cityName+"</option>");
	      	}
	    };
	    if(isFix){
	    		$("#selectCity").attr("disabled","disabled");
	    		showRegion();
	    }else {
   		//初始化筛选框
		$('.select-item select').each(function(){
			var val = $(this).find('option:selected').text();
			$(this).parents('.select-item').find('.select-txt').text(val);
		})
   		}
    } else{
   		sel.empty();  
    }
}

function showRegion(){
	var text = $("#selectCity  option:selected").text();
	if(text == "全部"){
		$("#queryCity").val("");
	}else{
		$("#queryCity").val(text);
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
	      	var queryRegion = "<%=queryRegion%>";
	      	if(fixRegion == item.regionName){
	      		sel.append("<option value = '"+item.regionId+"' selected>"+item.regionName+"</option>");
	      		isFix = true;
	      	}else if(queryRegion == item.regionName){
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
    	//初始化筛选框
    	$('.select-item select').each(function(){
    		var val = $(this).find('option:selected').text();
    		$(this).parents('.select-item').find('.select-txt').text(val);
    	})
}

function setRegion(){
	var text = $("#selectRegion  option:selected").text();
	if(text == "全部"){
		$("#queryRegion").val("");
	}else{
		$("#queryRegion").val(text);
	} 
}
//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});

function submitSheet() {
	$("#teacherForm").attr('action','TeacherInfoImport.do');
	$("#teacherForm").attr('method','post');
	$("#teacherForm").attr('enctype','multipart/form-data');
	$("#teacherForm").attr('encoding','multipart/form-data');
    document.getElementById("teacherForm").submit();
 }
</script>
</html>