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
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.business.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%
	TrainingClassInfoBean bean = (TrainingClassInfoBean)request.getAttribute("ClassInfo");
	TrainingOrganizationInfoBean trainingOrganizationInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	TrainingOrganizationInfoHelper trainingOrganizationInfoHelper = new TrainingOrganizationInfoHelper();
	ProcessLogHelper processLogHelper = new ProcessLogHelper();
	List<ProcessLogBean> processLogList = (List<ProcessLogBean>) request.getAttribute("ProcessLogList");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	String userName = (String)request.getAttribute("UserName");
	Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    Integer fileId = (Integer)request.getAttribute("fileId");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>学员招生</span>
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
                <div class="main-bd pl-32">
                    <section class="section-wrp">
                    	<table class="table table-base table-odd">
                    		<tr>
                    			<td class="frm-label">培训班名</td>
                    			<td class="frm-content"><%=bean.getClassName() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">培训机构</td>
                    			<td class="frm-content"><%=trainingOrganizationInfoBean.getOrganizationName() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">培育年份</td>
                    			<td class="frm-content"><%=bean.getTrainingYear() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">开班人数</td>
                    			<td class="frm-content"><%=bean.getClassPeopleQuantity() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">培训时间</td>
                    			<td class="frm-content"><%=bean.getTrainingTime() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">行程安排</td>
                    			<td class="frm-content"><%=bean.getTrainingSchedule() %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">流程状态</td>
                    			<td class="frm-content"><%=MapKit.getValueFromMap(bean.getRecruitStatus(), TrainingClassInfoConfig.RECRUIT_STATUS_MAP) %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">当前操作人</td>
                    			<td class="frm-content">
                    			<% String currentOperator = "";
							   if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_START)){
								   currentOperator = trainingOrganizationInfoBean.getOrganizationName();
							   }else if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_CONTINUE)){
								   currentOperator = bean.getRegion() + "主管部门";
							   }else if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH)){
								   currentOperator = "完成";
							   }
							 %>
							<%=currentOperator %>
                    			</td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">相关附件</td>
                    			<td class="frm-content">
                    				<div class="clearfix">
	                    				<div class="pop-box pop-mini fl mr-48 default">
	                    				    <div class="pop-book">
	                    				        <div class="book-wrp"><i class="icon-book">&nbsp;</i></div>
	                    				        <span class="wrp-title">
		                    						<span class="u-block">完整学员名单</span>
		                    						<span class="u-block unit-set js-unit"><a class="unit-a" onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileId %>'">下载</a></span>
		                    					</span>
	                    				    </div>
	                    				</div>
                    				</div>
                    				
                    			</td>
                    		</tr>
                    	</table>
                    </section>
					<div class="btn-wrp text-right">
					 <%if(!isGovernment) {%>
					<%if(!bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH) ){ %>
		           <form method="post" action="TrainingClassRecruitVerify.do" id="TrainingClassRecruitVerify">
	                  <input type="hidden" id="classId"  name="classId" value="<%=bean.getClassId() %>"/>
	                  <input type="hidden" id="isFinish"  name="isFinish" value=""/>
				      <a class="btn btn-mini btn-default js-reject" onclick="submitGo();">继续招生</a>
				      <a class="btn btn-mini btn-success js-pass" onclick="submitFinish();">完成招生</a>
		          </form>
		           <%} %>
				   <%} else{%>
				   <%if(!bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_FINISH)) {%>
				   <form method="post"  enctype="multipart/form-data" action="TrainingClassRecruitManage.do?classId=<%=bean.getClassId() %>" id="recruitImport">
			      <label class="btn btn-mini btn-default upload-wrp" for="file">
			      	<input type="file" id="file" name="file" multiple="multiple" onchange="submitForm();" style="width: 0; height: 0; position:static; top: 0; opacity:0; filter:alpha(opacity=0);" >
			      	上传新名单</label>
		           </form>
				   <%} %>
				   <%} %>
					</div>
					<section class="section-wrp mb-60">
						<h2 class="section-wrp-title">流转过程</h2>
                    	<table class="table table-base table-odd">
                    	<%for(ProcessLogBean processLogBean: processLogList) {%>
                    		<tr>
                    			<td><%=processLogHelper.getOperatorOrganizationName(processLogBean)%></td>
                    			<td><%=processLogHelper.getProcessTypeDescription(processLogBean)%></td>
                    			<td><%=DateKit.formatTimestamp(processLogBean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) %></td>
                    		</tr>
                        <%} %>
                        <!-- 当前流程 -->
                        <% String nextOperator = "";
							   String nextAction = "";
							   if(bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_START)){
								   nextOperator = trainingOrganizationInfoBean.getOrganizationName();
								   nextAction = "核实生源";
							   }else if( bean.getRecruitStatus().equals(TrainingClassInfoConfig.RECRUIT_CONTINUE)){
								   nextOperator = bean.getRegion() + "主管部门";
								   nextAction = "上传新名单";
							   }
							%>
							<tr>
                    			<td><%=nextOperator%></td>
                    			<td><%=nextAction %></td>
                    			<td></td>
                    		</tr>
                    	</table>
                    </section>
                </div>
            </div>
        </div>
    </div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/base.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
$(function(){
    mScroller();
})
function submitForm() {
       document.getElementById("recruitImport").submit();
 }
function submitFinish() {
	$("#isFinish").val("1");
       document.getElementById("TrainingClassRecruitVerify").submit();
       
 }

function submitGo() {
	$("#isFinish").val("0");
       document.getElementById("TrainingClassRecruitVerify").submit();
 }
</script>
</html>