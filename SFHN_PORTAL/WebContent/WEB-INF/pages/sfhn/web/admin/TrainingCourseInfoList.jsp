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
	List<TrainingCourseInfoBean> list = (List<TrainingCourseInfoBean>)request.getAttribute("TrainingCourseInfoList");
	Map<Integer,String> majorInfoMap = (Map<Integer,String>)request.getAttribute("MajorInfoMap");
	Map<Integer,String> majorTypeMap = (Map<Integer,String>)request.getAttribute("MajorTypeMap");
	List<MajorTypeBean> majorTypeList = (List<MajorTypeBean>)request.getAttribute("MajorTypeList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>课程信息库</span>
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
			       <form id="form">
                    <div class="search-box">
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
                                    <div class="form-label text-right col-xs-3">课程名:</div>
                                    <div class="col-xs-3">
                                        <input type="text" class="form-control" name="courseName" value="<%=ParamKit.getParameter(request, "courseName", "") %>">
                                    </div>
                                    <div class="col-xs-3">
                                        <button type="submit" class="btn btn-success btn-block">查询</button>
                                    </div>
                                    <%if(isGovernment){ %>
                                    <div class="col-xs-3">
                                        <button type="button" class="btn btn-success btn-block" onclick="javascript:location.href='TrainingCourseInfoManage.do'">新增课程</button>
                                    </div>
                                    <div class="col-xs-4">
                                        <a class="upload-wrp"><input type="file" id="file" name="file" value="上传" class="form-control" onchange="submitSheet();"  ><label  for="file">导入课程</label></a>
                                    </div>
                                    <%} %>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </form>
                 <section class="section-wrp section-scroll">             
                        <table class="table table-base table-odd">
                            <tr>
                                <th>课程名</th>
                                <th>类型</th>
	                            <th>专业</th>
	                            <th>操作</th>
                            </tr>
                            <%for(TrainingCourseInfoBean bean : list) {%>
                            <tr>
                                <td><%=bean.getCourseName() %></td>
                                <td><%=majorTypeMap.get(bean.getCourseId()) %></td>
                                <td><%=majorInfoMap.get(bean.getCourseId()) %></td>
                                <td> <%if(isGovernment){ %><a onclick="javascript:location.href='TrainingCourseInfoManage.do?courseId=<%=bean.getCourseId()%>'">编辑</a><%} %></td>
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
//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});
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
function submitSheet() {
	$("#form").attr('action','TrainingCourseInfoImport.do');
	$("#form").attr('method','post');
	$("#form").attr('enctype','multipart/form-data');
	$("#form").attr('encoding','multipart/form-data');
    document.getElementById("form").submit();
 }
</script>
</html>