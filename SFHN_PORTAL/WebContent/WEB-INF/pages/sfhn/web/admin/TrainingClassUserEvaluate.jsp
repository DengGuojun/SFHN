<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.framework.config.*"  %>
<%@ page import="com.lpmas.constant.user.*"  %>
<%@ page import="com.lpmas.framework.bean.*"  %>
<%@ page import="com.lpmas.framework.page.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.framework.web.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%@page import="com.lpmas.sfhn.portal.config.TrainingClassUserConfig"%>
<%
	List<TrainingClassUserBean> list = (List<TrainingClassUserBean>)request.getAttribute("ClassUserList");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	Map<Integer, DeclareReportBean> declareReportMap = (Map<Integer, DeclareReportBean>)request.getAttribute("DeclareReportMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	Map<Integer, String> industryTypeMap = (Map<Integer, String>)request.getAttribute("IndustryTypeMap");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    String organizationName = (String)request.getAttribute("OrganizationName");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>返回</span>
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
                <div class="main-bd pl-32">
                    <section class="section-wrp">
                        <h2 class="section-wrp-title"><%=classInfoBean.getClassName() %></h2>
                        <input type="hidden" name="classId" id="classId" value="<%=classInfoBean.getClassId() %>"/>	
                        <ul class="list-group">
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培训机构：</div>
                                    <div class="col-xs-10"><%=organizationName %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培育年份：</div>
                                    <div class="col-xs-10"><%=classInfoBean.getTrainingYear() %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">培育类型：</div>
                                    <div class="col-xs-10"><%=MapKit.getValueFromMap(classInfoBean.getTrainingType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">开班人数：</div>
                                    <div class="col-xs-10"><%=classInfoBean.getClassPeopleQuantity() %></div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">开班流程：</div>
                                    <div class="col-xs-10">
                                    <a onclick="javascript:location.href='TrainingClassInfoProcess.do?classId=<%=classInfoBean.getClassId()%>'" >查看详情</a>
                                    </div>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-xs-2">验收流程：</div>
                                    <div class="col-xs-10">
                                    <a onclick="javascript:location.href='TrainingClassAcceptProcess.do?classId=<%=classInfoBean.getClassId()%>'" >查看详情</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </section>
                    <section class="section-wrp">
                        <ul class="nav nav-tabs nav-justified">
                            <li><a href="TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>">学员信息</a></li>
                            <li><a href="TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>">集中培训材料</a></li>
                           <%--  <li><a href="TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>">田间实训材料</a></li> --%>
                            <li><a href="TrackingServiceInfoList.do?classId=<%=classInfoBean.getClassId()%>">跟踪服务</a></li>
                            <li class="active"><a href="TrainingClassUserEvaluate.do?classId=<%=classInfoBean.getClassId()%>">学员评定</a></li>
                        </ul>
                        <div class="nav-content">
                            <div class="nav-item">
                                <table class="table table-base table-odd table-center">
                                    <tr class="bortop-no">
                                        <th>姓名</th>
                                        <th>电话</th>
                                        <th>身份证号</th>                                       
                                        <th>类型</th>
                                        <th>考核及格</th>
                                        <th>认定通过</th>
                                    </tr>
                                    <%for(TrainingClassUserBean bean : list) {
				                      DeclareReportBean reportBean = declareReportMap.get(bean.getDeclareId());
		                            %>
                                    <tr>
                                        <td><%=reportBean.getUserName() %></td>
                                        <td><%=reportBean.getUserMobile() %></td>
                                        <td><%=reportBean.getIdentityNumber() %></td>
                                        <td><%=MapKit.getValueFromMap(reportBean.getDeclareType(), DeclareInfoConfig.DECLARE_TYPE_MAP) %></td>
                                        <td>
                                        <input type="checkbox" name="subBox" id="examResultApprove_<%=bean.getDeclareId() %>" <%=Constants.STATUS_VALID == bean.getExamResult() ? "checked" : ""%> value="<%=bean.getDeclareId() %>"/>
                                        </td>
                                        <td>
                                        <input type="checkbox" name="subBox2" id="authResultApprove_<%=bean.getDeclareId() %>" <%=Constants.STATUS_VALID == bean.getAuthResult() ? "checked" : ""%> value="<%=bean.getDeclareId() %>"/>
                                        </td>
                                    </tr>
                                    <%} %>
                                </table>
                            </div>
                        </div>                                         
                    </section>
                       <a class="btn btn-mini btn-default" id="examApprove">批量考核及格</a>   
                       <a class="btn btn-mini btn-default" id="authApprove">批量认定通过</a>   
                       <div class="btn-wrp text-right">                      
                       <a class="btn btn-mini btn-success" id="save">保存</a>
                       </div> 
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
$(function() {
	$("#examApprove").click(function() {
        $('input[name="subBox"]').prop("checked",true);
    });
	$("#authApprove").click(function() {
        $('input[name="subBox2"]').prop("checked",true); 
    });
	   $("#save").click(function() {
	    	  var checkStrexamResultApprove="";
	    	  $("input[name='subBox']:checked").each(function () {
	              if(checkStrexamResultApprove!="")    
	              {    
	            	  checkStrexamResultApprove+=",";    
	               }    
	              checkStrexamResultApprove += $(this).val(); 
	          });	
	    	  var checkStrauthResultApprove="";
	    	  $("input[name='subBox2']:checked").each(function () {
	              if(checkStrauthResultApprove!="")    
	              {    
	            	  checkStrauthResultApprove+=",";    
	               }    
	              checkStrauthResultApprove += $(this).val(); 
	          });
	    	  var classId = $('#classId').val();
	    	  $.ajax({
	              url: 'TrainingClassUserEvaluateManage.do',
	              data: {
	            	  checkStrexamResultApprove: checkStrexamResultApprove,
	            	  checkStrauthResultApprove: checkStrauthResultApprove,
	            	  classId:classId,
	              },
	              type: 'POST',
	              success: function (json) {
	                  console.log(json);
	                  if (json.code == '1') {
	                	  alert(json.message);
	                      window.location = 'TrainingClassUserEvaluate.do?classId='+<%=classInfoBean.getClassId() %>
	                  }
	                  else {
	                      alert(json.message);
	                  }
	              }
	          })
	 	});
});
</script>
</html>