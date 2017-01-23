<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
List<TrainingOrganizationInfoBean> list = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
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
        <script type="text/javascript" src="<%=STATIC_URL %>js/jquery-2.1.1.min.js"></script>
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>上传学员名单</span>
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
                    <form class="form-box col-xs-8" enctype="multipart/form-data" action="TrainingClassRecruitManage.do" id="TrainingClassRecruitManage" method="post">
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训机构</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="organizationId" id="organizationId" checkStr="培训机构;txt;true;;200" onchange="selectClass()">
									      <option value=""></option>
					                     <% int organizationId = ParamKit.getIntParameter(request, "organizationId", 0);
			                              for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : list){ %>
											<option value="<%=trainingOrganizationInfoBean.getOrganizationId() %>" <%=(trainingOrganizationInfoBean.getOrganizationId()==organizationId)?"selected":""%> ><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
										<%} %>
					                  </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训班</div>
                                <div class="col-xs-10">
                                    <select class="form-control" name="trainingClassId" id="trainingClassId" checkStr="培训班;txt;true;;200">
					                </select>
					                <input type="hidden" id="trainingClassInfoId" value="<%=ParamKit.getIntParameter(request, "trainingClassId", 0) %>"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">学员名单</div>
                                <div class="col-xs-10">
                                    <div class="row">
                                        <div class="col-xs-8">
                                            <input type="file" id="file" name="file" value="上传" class="form-control">
                                        </div>
                                        <!-- <div class="col-xs-4">
                                            <a class="download-link">模板下载</a>
                                        </div> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="text-center btn-wrp">
                            <button class="btn first btn-success btn-mini" onclick="submitForm();">提交</button>
                        </div>
                    </form>                    
                    
                </div>
            </div>
        </div>
    </div>	
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
mScroller();
$(document).ready(function() {
	selectClass();
	 $(".select-item select").trigger('change');
	});
//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});
function submitForm() {
	var orgAction = $("#TrainingClassRecruitManage").attr('action');
	var trainingClassId = $("#trainingClassId").val();   
	$("#TrainingClassRecruitManage").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassRecruitManage").submit();
 }

function selectClass(){
	var organizationId=$("#organizationId").val();  
	var trainingClassInfoId = $("#trainingClassInfoId").val();
	if(organizationId != null){
		var params={  
		        'organizationId':organizationId  
	    };  
	    $.ajax({
	        type: 'get',
	        url: "/sfhn/admin/TrainingClassJsonList.do",
	        data: params,
	        dataType: 'json',
	        success: function(data){
		      	var sel2 = $("#trainingClassId");  
	      		sel2.empty();  
		      	if(data==null) {
	      		 	sel2.append("<option value =''> </option>");
	          	}else {
	          		var items=data.result;
		    	    		if(items!=null) {
		    	    	  	sel2.append("<option value =''> </option>");
		        	  	for(var i =0;i<items.length;i++) {
		          		var item=items[i];
		    	      		if(item.classId==trainingClassInfoId){
			          			sel2.append("<option value = '"+item.classId+"' selected>"+item.className+"</option>");
			          		}else{
			            			sel2.append("<option value = '"+item.classId+"'>"+item.className+"</option>");
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