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
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	FileInfoBean fieldTrainingPlan = (FileInfoBean)request.getAttribute("FieldTrainingPlan");
	Map<Integer, FileInfoBean> fileInfoMap  = (Map<Integer, FileInfoBean>)request.getAttribute("FileInfoMap");
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
                    <span class="u-title"><a href="javascript:location.href='TrainingClassInfoList.do'"><i class="icon-return"></i></a>返回</span>
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
                            <li ><a href="TrainingClassTeacherList.do?classId=<%=classInfoBean.getClassId()%>">教师信息</a></li>
                            <li class="active"><a href="TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>">集中培训材料</a></li>
                           <%--  <li><a href="TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>">田间实训材料</a></li> --%>
                            <li><a href="TrackingServiceInfoList.do?classId=<%=classInfoBean.getClassId()%>">跟踪服务</a></li>
                             <%if(isGovernment){ %>
                            <li><a href="TrainingClassUserEvaluate.do?classId=<%=classInfoBean.getClassId()%>">学员评定</a></li>
                            <%} %>
                        </ul>
                        <div class="nav-content">
                            <div class="nav-item">
                                <table class="table table-base table-odd">
                                <input type="hidden" id="classId" value="<%=classInfoBean.getClassId() %>"/>
                    		<tr class="bortop-no">
                    		    <th>序号</th>
                    			<th>资料</th>
                    			<th>支持文件类型</th>
                    			<th>状态</th>
                    			<th>时间</th>
                    			<th>操作</th>
                    		</tr>
                    		<tr>
                    		    <td>1</td>
                    			<td>教材封面及目录</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                    			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<%}%>   
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_TRAINING_MATERIAL_CATALOG%>'" class="">查看</a>
								</td>                     			         
                    		</tr>
                    		<tr>
                    		    <td>2</td>
                    			<td>教师聘书</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_APPOINTMENT) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_APPOINTMENT).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_APPOINTMENT).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_APPOINTMENT).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                    			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<%}%>   
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_TEACHER_APPOINTMENT%>'" class="">查看</a>
								</td>                   			
                    		</tr>
                    		<tr>
                    		    <td>3</td>
                    			<td>培训教师师资信息</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getFileId()%>'" class="">下载</a>  
                    			<%if(!isGovernment) {%>       
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>|      
								<label class="ml-10 btn-upload upload-wrp" for="file3"><a>更换</a><input type="file" id="file3" onchange="up('file3',<%=FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION %>);"></label>							
								<%} %>                  		                			
                         	    </td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file3" onchange="up('file3',<%=FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION %>);"></label>
								<%} %></td>
                  			    <%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TEACHER_INFORMATION).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>4</td>
                    			<td>班委会名单</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getFileId()%>'" class="">下载</a>  
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file4" onchange="up('file4',<%=FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST %>);"></label>
								<%} %>                   			                 			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file4" onchange="up('file4',<%=FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_COMMITTEE_LIST).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>5</td>
                    			<td>学员考勤表</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_EXCEL%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                   			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_ATTENDANCE_LIST%>'" class="">查看</a>
								</td>                    			
                    		</tr>
                    		<tr>
                    		    <td>6</td>
                    			<td>结业证书发放表</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_EXCEL%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CERTIFICATE_OF_COMPLETION) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CERTIFICATE_OF_COMPLETION).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CERTIFICATE_OF_COMPLETION).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CERTIFICATE_OF_COMPLETION).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                   			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CERTIFICATE_OF_COMPLETION%>'" class="">查看</a>
								</td>      
                    		</tr>
                    		<tr>
                    		    <td>7</td>
                    			<td>学员满意度测评汇总表</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getFileId()%>'" class="">下载</a>  
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file7" onchange="up('file7',<%=FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY %>);"></label>
								<%} %>                    			                			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file7" onchange="up('file7',<%=FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_SATISFACTION_SURVEY).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>8</td>
                    			<td>考试考核材料（成绩汇总表）</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_EXCEL%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT) !=null){%>
                    			<td>已上传</td> 
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                   			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_EXAM_RESULT%>'" class="">查看</a>
								</td>         
                    		</tr>
                    		<tr>
                    		    <td>9</td>
                    			<td>跟踪管理协议</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL) !=null){%>
                    			<td>已上传</td> 
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                   			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_TRACKING_MANAGEMENT_PROTOCOL%>'" class="">查看</a>
								</td>         
                    		</tr>
                    		<tr>
                    		    <td>10</td>
                    			<td>培育资金使用计划</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_EXCEL%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getFileId()%>'" class="">下载</a>  
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file10" onchange="up('file10',<%=FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN %>);"></label>
								<%} %>                    			                 			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file10" onchange="up('file10',<%=FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_FUND_USE_PLAN).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>11</td>
                    			<td>学员台帐</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_EXCEL%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET) !=null){%>
                    			<td>已上传</td>  
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                  			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_USER_SHEET%>'" class="">查看</a>
								</td>        
                    		</tr>
                    		<%-- <tr>
                    		    <td>12</td>
                    			<td>认定申报表</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM) !=null){%>
                    			<td>已上传</td>  
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                 			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_IDENTIFY_DECLARE_FORM%>'" class="">查看</a>
								</td>          
                    		</tr> --%>
                    		<tr>
                    		    <td>12</td>
                    			<td>认定信息采集表</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                   			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_INFORMATION_ACAUISITION_FORM%>'" class="">查看</a>
								</td>          
                    		</tr>
                    		<tr>
                    		    <td>13</td>
                    			<td>身份证复印件</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY) !=null){%>
                    			<td>已上传</td>   
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>               			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_IDENTITY_CARD_COPY%>'" class="">查看</a>
								</td>                    			
                    		</tr> 
                    		<tr>
                    		    <td>14</td>
                    			<td>典型学员材料</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getFileId()%>'" class="">下载</a>  
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file15" onchange="up('file15',<%=FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER %>);"></label>
								<%} %>                   			                 			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file15" onchange="up('file15',<%=FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TYPICAL_CLASS_USER).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>15</td>
                    			<td>培训班总结</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getFileId()%>'" class="">下载</a> 
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file16" onchange="up('file16',<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY %>);"></label>
								<%} %>                   			                   			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file16" onchange="up('file16',<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_TRAINING_CLASS_SUMMARY).getFileId(): "" %>" />           
                    		</tr>
                    		<tr>
                    		    <td>16</td>
                    			<td>宣传材料</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_PROMOTIONAL_MATERIAL) !=null){%>
                    			<td>已上传</td>           
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_PROMOTIONAL_MATERIAL).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_PROMOTIONAL_MATERIAL).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_PROMOTIONAL_MATERIAL).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>         			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>                    			
                    			<%}%>  
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_PROMOTIONAL_MATERIAL%>'" class="">查看</a>
								</td>                    			
                    		</tr> 
                    		<tr>
                    		    <td>17</td>
                    			<td>参观基地简介</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>  
                    			<td>
                    			<a onclick="javascript:location.href='FileInfoDownload.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getFileId()%>'" class="">下载</a> 
                    			<%if(!isGovernment) {%>
                    			|<a onclick="javascript:location.href='FileInfoRemove.do?fileId=<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getFileId()%>&url=TrainingClassCentralizedManage'" class="">删除</a>| 
								<label class="ml-10 btn-upload upload-wrp"><a>更换</a><input type="file" id="file18" onchange="up('file18',<%=FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION %>);"></label>
								<%} %>                 			
                    			</td>
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<td><%if(!isGovernment) {%>
                    			<label class="upload-wrp btn-upload"><a>上传</a><input type="file" id="file18" onchange="up('file18',<%=FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION %>);"></label>
								<%} %></td>
                    			<%}%>   
                    			<input type="hidden" id="<%=FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION %>_id" value="<%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION)!=null ? fileInfoMap.get(FileInfoConfig.FILE_TYPE_VISIT_BASE_INTRODUCTION).getFileId(): "" %>" />           
                    		</tr>                   		               		                   		
                    		<tr>
                    		    <td>18</td>
                    			<td>培训现场照片</td>
                    			<td><%=FileInfoConfig.FILE_FORMAT_PIC_OR_WORD%></td>
                    			<%if(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_PHOTO) !=null){%>
                    			<td>已上传</td>
                    			<td><%=fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_PHOTO).getModifyTime() == null ? DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_PHOTO).getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) : DateKit.formatTimestamp(fileInfoMap.get(FileInfoConfig.FILE_TYPE_CLASS_PHOTO).getModifyTime(), DateKit.DEFAULT_DATE_TIME_FORMAT)%></td>                     			
                    			<%} else{%>
                    			<td>未上传</td>
                    			<td></td>
                    			<%}%>      
                    			<td>                    		
								<a onclick="javascript:location.href='TrainingClassImageList.do?classId=<%=classInfoBean.getClassId()%>&fileType=<%=FileInfoConfig.FILE_TYPE_CLASS_PHOTO%>'" class="">查看</a>
								</td>                			
                    		</tr>                    		                   		                   		
                    	    </table>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
		<div class="main-bd">
          <div class="loading-mask loading-box dn"><img src="<%=STATIC_URL %>images/loading.gif"></div>
       </div>
	</div>
</body>
<script src="<%=STATIC_URL %>js/jquery.min.js"></script>
<script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
<script src="<%=STATIC_URL %>js/mScroller.js"></script>
<script type="text/javascript">
mScroller();
function up(fileInput,fileType) {
	$('.loading-box').removeClass('dn');
    var file_data = $('#'+fileInput)[0].files; // for multiple files
    var existId = $('#'+fileType+'_id').val();
    var classId = $("#classId").val();
    var infoType = '<%=InfoTypeConfig.INFO_TYPE_TRAINING_CLASS_INFO%>';
    for (var i = 0; i < file_data.length; i++) {
        var fd = new FormData();
        fd.append('file', file_data[i]);
        $.ajax({
        		url: 'FileInfoUpload.do?fileId='+existId+'&infoId1='+classId+'&infoType='+infoType+"&fileType="+fileType,
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.code == '1'){
                		alert("上传成功");
                		var url = "TrainingClassCentralizedManage.do?classId=" + classId;
                		window.location.href= url
                }else{
                		alert(data.message);
                }
                $('.loading-box').addClass('dn');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}
function remove(fileType) {
	$('.loading-box').removeClass('dn');
    var existId = $('#'+fileType+'_id').val();
        $.ajax({
        		url: 'FileInfoRemove.do?fileId='+existId+"&fileType="+fileType,
            data: fd,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function (data) {
                console.log(data);
                if(data.code == '1'){
                		alert("删除成功");
                		var url = "TrainingClassCentralizedManage.do?classId=" + classId;
                		window.location.href= url
                }else{
                		alert(data.message);
                }
                $('.loading-box').addClass('dn');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    
}
</script>
</html>