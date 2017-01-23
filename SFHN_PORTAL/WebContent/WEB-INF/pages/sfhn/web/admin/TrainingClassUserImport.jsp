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
List<TrainingClassInfoBean> list = (List<TrainingClassInfoBean>)request.getAttribute("trainingClassInfoList");
String organizationName = (String)request.getAttribute("organizationName");
List<TrainingOrganizationInfoBean> trainingOrganizationInfoList = (List<TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrganizationInfoList");
Boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
String userName = (String)request.getAttribute("UserName");
Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
List<String> messageList = (List<String>)request.getAttribute("message");
List<String> messageDeleteList = (List<String>)request.getAttribute("messageDelete");
Integer organizationId = ParamKit.getIntParameter(request, "organizationId", 0);
int trainingClassId = ParamKit.getIntParameter(request, "classId", 0);
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
                    <span class="u-title"><a href="Index.do"><i class="icon-return"></i></a>导入学员台账</span>
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
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">培训机构</div>
                                <div class="col-xs-10">
                                    <%if(!isGovernment){ %>
                                    <span><%=organizationName %></span>
                                    <%}else{ %>
                                    <select class="form-control" name="organizationId" id="organizationId" onchange="selectClass()">
		                            <option value="">全部</option>
		                            <%for(TrainingOrganizationInfoBean trainingOrganizationInfoBean : trainingOrganizationInfoList){ %>
							        <option value="<%=trainingOrganizationInfoBean.getOrganizationId()%>" <%=organizationId == trainingOrganizationInfoBean.getOrganizationId()? "selected" : ""%>><%=trainingOrganizationInfoBean.getOrganizationName() %></option>
					                <%} %>
	                                </select>
	                                <%} %>
                                </div>
                            </div>
                            <div class="form-group select-item">
                                <div class="col-xs-2 form-label">培训班</div>
                                <div class="col-xs-10">
                                    <%if(!isGovernment){ %>
                                     <select class="form-control" name="classId" id="classId" checkStr="培训班;txt;true;;200">
					                     <%for(TrainingClassInfoBean trainingClassInfoBean : list){ %>
											<option value="<%=trainingClassInfoBean.getClassId()%>" <%=(trainingClassInfoBean.getClassId()==trainingClassId)?"selected":""%> ><%=trainingClassInfoBean.getClassName() %></option>
										<%} %>
					                  </select>
					                  <%}else{ %>
					                  <select class="form-control" name="classId" id="classId" checkStr="培训班;txt;true;;200">
					                  </select>
					                  <%} %>
                                </div>
                            </div>
                            <form method="post" enctype="multipart/form-data" action="TrainingClassUserImport.do" id="TrainingClassUserImport">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">台账文件</div>
                                <div class="col-xs-10">
                                    <div class="row">                                      
                                        <div class="col-xs-8">
                                            <input type="file" id="file" name="file" value="上传" class="form-control" onchange="submitSheet();">
                                        </div>                                      
                                       <!--  <div class="col-xs-4">
                                            <a class="download-link" onclick="downloadFile()">台账模板下载</a>
                                        </div> -->
                                    </div>
                                </div>
                            </div>
                            </form>
                            <form method="post" enctype="multipart/form-data" action="TrainingClassUserDetailImport.do" id="TrainingClassUserDetailImport">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">学员信息表</div>
                                <div class="col-xs-10">
                                    <div class="row">                                
                                        <div class="col-xs-12">                                       
                                            <input type="file" id="file" name="file" multiple="multiple" value="上传" class="form-control">
                                        </div>                                    
                                    </div>
                                </div>
                            </div>
                            </form>
                        </div>
                        <p class="bg-info pd-15">提示：学员信息表可多选，系统将根据信息表的命名与台帐中的身份证号自动匹配确定学员信息</p>
                        <div class="text-center btn-wrp">
                        	<button type="button" class="btn first btn-default btn-mini" id="cancel">取消</button>
                            <button type="button" class="btn btn-success btn-mini" onclick="submitForm();">提交</button>
                        </div>
                     <%if(messageList != null &&!messageList.isEmpty()){ %>                   
                    <hr>
                    <section class="section-wrp">     
                       <%if(messageList.size() > 0){ %>                  
                        <h2 class="section-wrp-title"><%=messageList.get(0) %>
                        </h2>  
                        <%} %>             
                        <table class="table table-base table-odd">
                        <%for(int i = 1; i < messageList.size(); i++) { %>
                        <%String[] sourceStrArray = messageList.get(i).split(",", 3); %>
                            <tr>
                            	<%for(String str : sourceStrArray){ %>
                                <td><%=str%></td>
                             <%} %>
                            </tr>
                          <%} %>
                        </table>
                    </section>
                    <%} %>
                  <%if(messageDeleteList != null &&!messageDeleteList.isEmpty()){ %>
                  <hr>
			       <section class="section-wrp">
				    <table class="table table-base table-odd">
				<%for(int i = 0; i < messageDeleteList.size(); i++) { %>
				<%String[] sourceStrArray = messageDeleteList.get(i).split(",", 3); %>
		                     <tr>
                                <td><%=sourceStrArray[0]%></td>
                                <td><%=sourceStrArray[1]%></td>
                                <td><%=sourceStrArray[2]%></td>
                            </tr>				  
		         <%} %>
		          </table>
			       </section>
			      <%} %>               
                </div>
            </div>
        </div>
    </div>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>		
<script src="<%=STATIC_URL %>js/jquery.ui.widget.js"></script>
<script src="<%=STATIC_URL %>js/jquery.iframe-transport.js"></script>
<script src="<%=STATIC_URL %>js/jquery.fileupload.js"></script>
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%=STATIC_URL %>/js/jquery.xdr-transport.js"></script>
<![endif]-->
<script type="text/javascript">
$(document).ready(function() {
	 $(".select-item select").trigger('change');
	 $("#cancel").click(function() {
			if(confirm("确定要放弃导入吗?")){
				var url = "Index.do" ;
				window.location.href= url
			 }
		});
});
$(function(){
    mScroller();

})
function downloadFile(){
	var trainingClassId = $("#classId").val(); 
	var str="<%=PORTAL_URL %>/file/admin_file/template/<%=trainingClassId%>.xls";     
    window.frames["hrong"].location.href   =   str; 
}
function submitSheet() {
	var orgAction = $("#TrainingClassUserImport").attr('action');
	var trainingClassId = $("#classId").val();   
	$("#TrainingClassUserImport").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassUserImport").submit();
 }

//选项
$('.select-item select').change(function(){
	var val = $(this).children('option:selected').text();
	$(this).parents('.select-item').find('.select-txt').text(val);
});

function submitForm() {
	var orgAction = $("#TrainingClassUserDetailImport").attr('action');
	var trainingClassId = $("#classId").val();   
	$("#TrainingClassUserDetailImport").attr('action',orgAction+'?classId='+trainingClassId);
       document.getElementById("TrainingClassUserDetailImport").submit();
 }
function selectClass(){
	var organizationId=$("#organizationId").val();  
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
		      	var sel2 = $("#classId");  
	      		sel2.empty();  
		      	if(data==null) {
	      		 	sel2.append("<option value =''> </option>");
	          	}else {
	          		var items=data.result;
		    	    		if(items!=null) {
		    	    	  	sel2.append("<option value =''> </option>");
		        	  	for(var i =0;i<items.length;i++) {
		          		var item=items[i];
		    	      		sel2.append("<option value = '"+item.classId+"'>"+item.className+"</option>");
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
</body>
</html>