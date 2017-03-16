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
	TeacherEvaluateBean bean = (TeacherEvaluateBean)request.getAttribute("TeacherEvaluateBean");
	TeacherInfoBean teacherInfoBean = (TeacherInfoBean)request.getAttribute("TeacherInfoBean");
	TrainingCourseInfoBean courseInfoBean = (TrainingCourseInfoBean)request.getAttribute("CourseInfoBean");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("ClassInfoBean");
	GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
	TrainingOrganizationInfoBean trainingOrgInfoBean = (TrainingOrganizationInfoBean)request.getAttribute("TrainingOrgInfoBean");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
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
                    		<input type="hidden" name="evaluateId" id="evaluateId" value="<%=bean.getEvaluateId() %>"/>
                    		<input type="hidden" name="teacherId" id="teacherId" value="<%=bean.getTeacherId() %>"/>
                    		<input type="hidden" name="classId" id="classId" value="<%=bean.getClassId() %>"/>
                    		<input type="hidden" name="courseId" id="courseId" value="<%=bean.getCourseId() %>"/>
                    		<input type="hidden" name="organizationType" id="organizationType" value="<%=isGovernment ? InfoTypeConfig.INFO_TYPE_GOVERNMENT_ORGANIZATION : InfoTypeConfig.INFO_TYPE_TRAINING_ORGANIZATION %>"/>
                    		<input type="hidden" name="organizationId" id="organizationId" value="<%=isGovernment ? governmentOrgInfoBean.getOrganizationId() : trainingOrgInfoBean.getOrganizationId()%>"/>
                    		<input type="hidden" name="status" id="status" value="<%=bean.getStatus() %>"/>
                        <div class="section-wrp form-horizontal">
                            <div class="form-group">
                                <div class="col-xs-2 form-label">评分</div>
                                <div class="col-xs-2">
                                    <select name="evaluateScore" class="form-control" checkStr="评分;txt;true;;30">
										<option value="5" <%=bean.getEvaluateScore() == 5 ? "selected" : ""%>>5</option>
										<option value="4" <%=bean.getEvaluateScore() == 4 ? "selected" : ""%>>4</option>
										<option value="3" <%=bean.getEvaluateScore() == 3 ? "selected" : ""%>>3</option>
										<option value="2" <%=bean.getEvaluateScore() == 2 ? "selected" : ""%>>2</option>
										<option value="1" <%=bean.getEvaluateScore() == 1 ? "selected" : ""%>>1</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">课程</div>
                                <div class="col-xs-10">
                                    <%=courseInfoBean.getCourseName() %>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">年份</div>
                                <div class="col-xs-10">
                                    <%=classInfoBean.getTrainingYear() %>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">班级</div>
                                <div class="col-xs-10">
                                    <%=classInfoBean.getClassName() %>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-2 form-label">等级</div>
                                <div class="col-xs-10">
                                    <%=isGovernment ? GovernmentOrganizationConfig.ORGANIZATION_LEVEL_MAP.get(governmentOrgInfoBean.getOrganizationLevel()) : "培训机构" %>
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
</html>