<%@page import="com.lpmas.sfhn.config.TrainingOrganizationConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	ProcessLogHelper processLogHelper = new ProcessLogHelper();
	List<ProcessLogBean> processLogList = (List<ProcessLogBean>) request.getAttribute("ProcessLogList");
	String logContent = (String) request.getAttribute("LogContent");
	boolean HasApprovePermission = (Boolean) request.getAttribute("HasApprovePermission");
	String userName = (String)request.getAttribute("UserName");
	Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    Map<Integer, Integer> fileInfoMap  = (Map<Integer, Integer>)request.getAttribute("FileInfoMap");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>新开班申请</span>
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
                    			<td class="frm-label">班级类型</td>
                    			<td class="frm-content"><%=MapKit.getValueFromMap(bean.getClassType(), TrainingClassInfoConfig.CLASS_TYPE_MAP) %></td>
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
                    			<td class="frm-content"><%=MapKit.getValueFromMap(bean.getClassStatus(), TrainingClassInfoConfig.CLASS_STATUS_MAP) %></td>
                    		</tr>
                    		<tr>
                    			<td class="frm-label">当前操作人</td>
                    			<td class="frm-content">
                    			<% String currentOperator = "";
							   if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT) 
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION)
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY)
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE)){
								   currentOperator = trainingOrganizationInfoBean.getOrganizationName();
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)){
								   currentOperator = bean.getRegion() + "主管部门";
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)){
								   currentOperator = bean.getCity() + "主管部门";
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)){
								   currentOperator = bean.getProvince() + "主管部门";
							   }else{
								   currentOperator = "完成";
							   }
							%>
							<%=currentOperator %>
                    			</td>
                    		</tr>
                    		<%if(StringKit.isValid(logContent)){%>	
						    <tr>
						    <td class="frm-label">操作意见</td>
						    <td class="frm-content"><%=logContent%></td>
						    </tr>
						   <%}%>
                    		<tr>
                    			<td class="frm-label">相关附件</td>
                    			<td class="frm-content">
                    				<div class="clearfix">
	                    				<div class="pop-box pop-mini fl mr-48 default">
	                    				    <div class="pop-book">
	                    				        <div class="book-wrp"><i class="icon-book">&nbsp;</i></div>
	                    				        <span class="wrp-title">
		                    						<span class="u-block">培育工程项目申报表</span>
		                    						<span class="u-block unit-set js-unit"><a class="unit-a" href="FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_PROJECT_DECLARE_FORM)%>">下载</a></span>
		                    					</span>
	                    				    </div>
	                    				</div>
	                    				<div class="pop-box pop-mini fl mr-48 default">
	                    				    <div class="pop-book">
	                    				        <div class="book-wrp"><i class="icon-book">&nbsp;</i></div>
	                    				        <span class="wrp-title">
		                    						<span class="u-block">项目年度实施计划</span>
		                    						<span class="u-block unit-set js-unit"><a class="unit-a" href="FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_ANNUAL_PLAN)%>">下载</a></span>
		                    					</span>
	                    				    </div>
	                    				</div>
	                    				<div class="pop-box pop-mini fl mr-48 default">
	                    				    <div class="pop-book">
	                    				        <div class="book-wrp"><i class="icon-book">&nbsp;</i></div>
	                    				        <span class="wrp-title">
		                    						<span class="u-block">培训指南</span>
		                    						<span class="u-block unit-set js-unit"><a class="unit-a" href="FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_GUIDE)%>">下载</a></span>
		                    					</span>
	                    				    </div>
	                    				</div>
                    				</div>
                    				
                    			</td>
                    		</tr>
                    	</table>
                    </section>
					<div class="btn-wrp text-right">
					<%if(!isGovernment && (bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE)) ) {%>
				    <a class="btn btn-mini btn-default" onclick="javascript:location.href='TrainingClassInfoManage.do?classId=<%=bean.getClassId() %>'">
					修改
				    </a>
				   <%} %>
				   <%if(!isGovernment && (bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY)
						|| bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE)) ) {%>
				    <a class="btn btn-mini btn-default" onclick="javascript:location.href='TrainingClassInfoCommit.do?classId=<%=bean.getClassId() %>&action=<%=TrainingClassInfoConfig.COMMIT_ACTION_COMMIT%>'">
					重新提交
				    </a>
				  <%} %>
				  <%if(!isGovernment && bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)){%>
				<a class="btn btn-mini btn-default" onclick="javascript:location.href='TrainingClassInfoCommit.do?classId=<%=bean.getClassId() %>&action=<%=TrainingClassInfoConfig.COMMIT_ACTION_CANCEL_COMMIT%>'">
					撤回
				 </a>
				<%} %>
					<%if(HasApprovePermission) {%>
					<a class="btn btn-mini btn-default js-reject">驳回</a>
					<a class="btn btn-mini btn-success js-pass">通过</a>
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
							   if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_EDIT) 
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_REGION)
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_CITY)
									   || bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_REJECTED_PROVINCE)){
								   nextOperator = trainingOrganizationInfoBean.getOrganizationName();
								   nextAction = "待修改";
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_REGION)){
								   nextOperator = bean.getRegion() + "主管部门";
								   nextAction = "待县主管部门审批";
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_CITY)){
								   nextOperator = bean.getCity() + "主管部门";
								   nextAction = "待市主管部门审批";
							   }else if(bean.getClassStatus().equals(TrainingClassInfoConfig.CLASS_STATUS_WAIT_APPROVE_PROVINCE)){
								   nextOperator = bean.getProvince() + "主管部门";
								   nextAction = "待省主管部门审批";
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
	<!-- 弹窗 start -->
    <div id="pass-reason" class="panel panel-default popup-box dn">
    <form id="formData1" class="form-horizontal" method="post" action="TrainingClassInfoApprove.do" onsubmit="javascript:return checkForm('formData1');">
      <div class="panel-heading">审批通过</div>
      <div class="panel-body">
        <textarea class="form-control" rows="5" name="reason" placeholder="主要问题及意见" checkStr="主要问题及意见;txt;true;;1000"> </textarea>
        <input type="hidden" name="action" id="action" value="<%=TrainingClassInfoConfig.APPROVE_ACTION_PASS%>"/>
		<input type="hidden" name="classId" value="<%=bean.getClassId() %>"/>	      
      </div>
      <div class="panel-footer text-center">
          <button type="button" class="btn btn-default btn-popup js-close">取消</button>
          <button class="btn btn-success btn-popup ml-10">确定通过</button>
      </div>
      </form>
    </div>
    <div id="reject-reason" class="panel panel-default popup-box dn">
    <form id="formData2" class="form-horizontal" method="post" action="TrainingClassInfoApprove.do" onsubmit="javascript:return checkForm('formData2');">
      <div class="panel-heading">审批不通过</div>
      <div class="panel-body">
        <textarea class="form-control" rows="5" name="reason" placeholder="主要问题及整改意见" checkStr="主要问题及整改意见;txt;true;;1000"> </textarea>
        <input type="hidden" name="action" id="action" value="<%=TrainingClassInfoConfig.APPROVE_ACTION_FAIL%>"/>
		<input type="hidden" name="classId" value="<%=bean.getClassId() %>"/>	      
      </div>
      <div class="panel-footer text-center">
          <button type="button" class="btn btn-default btn-popup js-close">取消</button>
          <button class="btn btn-success btn-popup ml-10">提交</button>
      </div>
      </form>
    </div>  
    <!-- 弹窗 end -->
    <div class="u-mask dn"></div>
    <script src="<%=STATIC_URL %>js/jquery.min.js"></script>
    <script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
    <script src="<%=STATIC_URL %>js/base.js"></script>
    <script src="<%=STATIC_URL %>js/mScroller.js"></script>
    <script type="text/javascript">
        $(function(){
            mScroller();
            
            $('.js-pass').click(function(){
        		if($('.js-unit.selected').length != $('.js-unit').length){
        			alert("请先查阅附件");
        			return;
        		}
        		$('.u-mask').removeClass('dn');
        		$('#pass-reason').removeClass('dn');
        	});
        	
        	$('.js-reject').click(function(){
        		if($('.js-unit.selected').length != $('.js-unit').length){
        			alert("请先查阅附件");
        			return;
        		}
        		$('.u-mask').removeClass('dn');
        		$('#reject-reason').removeClass('dn');
        	});
        	
        	$('.js-close, .u-mask').click(function(){
        		$('.u-mask').addClass('dn');
        		$('.popup-box').addClass('dn');
        	});
        	
        	$('.js-unit').click(function(){
        		if($(this).hasClass('selected')){
        		}else{
        			$(this).addClass('selected');
        		}
        	});
        })
    </script>
</body>
</html>