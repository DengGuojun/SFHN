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
	TrainingClassTeacherBean bean = (TrainingClassTeacherBean)request.getAttribute("TrainingClassTeacherBean");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	TeacherInfoBean teacherInfoBean = (TeacherInfoBean)request.getAttribute("TeacherInfoBean");
	List<MajorTypeBean> majorTypeList = (List<MajorTypeBean>)request.getAttribute("MajorTypeList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	String userName = (String)request.getAttribute("UserName");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>选择课程</span>
                    <div class="dropdown fr ">
                      <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        个人设置
                        <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                        <li><a href="http://passport.1haowenjian.cn/user/UserInfoManage.do">个人设置</a></li>
                        <li><a href="http://passport.1haowenjian.cn/user/Logout.do">退出</a></li>
                      </ul>
                    </div>
                </div>
                <div class="main-bd">
                    <form class="form-box col-xs-8" id="formData" method="post"  onsubmit="javascript:return checkForm('formData');" action="TrainingClassTeacherManage.do">
                    		<input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
                    		<input type="hidden" name="teacherId" id="teacherId" value="<%=teacherInfoBean.getTeacherId() %>"/>
                    		<input type="hidden" name="classId" id="classId" value="<%=classInfoBean.getClassId() %>"/>
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">课程名</div>
                                <div class="col-xs-10">
                                    <input type="text" class="form-control" name="courseName"  value="" checkStr="课程名;txt;true;;100">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">专业</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <select style="width: 49%; float: left;" class="form-control" id="majorType" name="majorTypeId" onchange="typeSelectionChange();" checkStr="专业;txt;true;;200">
                                                 <%for(MajorTypeBean typeBean  : majorTypeList){ %>
												<option value="<%=typeBean.getMajorId()%>"  ><%=typeBean.getMajorName() %></option>
												<%} %>
                                            </select>
                                            <select style="width: 49%; float: left; margin-left: 2%;"  class="form-control" id="majorInfo" name="majorId" checkStr="专业;txt;true;;200">
                                                <option value=""></option>
                                            </select>
                                            <input type="hidden" id="majorInfoId" value=""/>
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

</script>
</html>