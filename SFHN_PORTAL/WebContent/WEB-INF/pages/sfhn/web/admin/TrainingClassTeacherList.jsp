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
<%@ page import="com.lpmas.sfhn.declare.config.*"  %>
<%@ page import="com.lpmas.sfhn.declare.bean.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%
	List<TrainingClassTeacherBean> list = (List<TrainingClassTeacherBean>)request.getAttribute("TrainingClassTeacherList");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	Map<Integer, String> teacherInfoMap = (Map<Integer, String>)request.getAttribute("TeacherInfoMap");
	Map<Integer, String> courseInfoMap = (Map<Integer, String>)request.getAttribute("CourseInfoMap");
	Map<Integer, String> majorInfoMap = (Map<Integer, String>)request.getAttribute("MajorInfoMap");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    String organizationName = (String)request.getAttribute("OrganizationName");
    Map<String, Integer> fileInfoMap = (Map<String, Integer>)request.getAttribute("fileInfoMap");
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
                            <li><a href="http://passport.1haowenjian.cn/user/Logout.do">退出</a></li>
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
                            <li ><a href="TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>">学员信息</a></li>
                            <li class="active" ><a href="TrainingClassTeacherList.do?classId=<%=classInfoBean.getClassId()%>">教师信息</a></li>
                            <li><a href="TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>">集中培训材料</a></li>
                            <li><a href="TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>">田间实训材料</a></li>
                            <li><a href="TrackingServiceInfoList.do?classId=<%=classInfoBean.getClassId()%>">跟踪服务</a></li>
                             <%if(isGovernment){ %>
                            <li><a href="TrainingClassUserEvaluate.do?classId=<%=classInfoBean.getClassId()%>">学员评定</a></li>
                            <%} %>
                        </ul>
                        <div class="nav-content">
                            <div class="nav-item">
                                <table class="table table-base table-odd table-center">
                                    <tr class="bortop-no">
                                        <th>教师</th>
                                        <th>专业</th>
                                        <th>课程</th>
                                        <th>操作</th>
                                    </tr>
                                    <%for(TrainingClassTeacherBean bean : list) {%>
                                    <tr>
                                        <td><%=MapKit.getValueFromMap(bean.getTeacherId(), teacherInfoMap)%></td>
                                        <td><%=MapKit.getValueFromMap(bean.getTeacherId(), majorInfoMap)%></td>
                                        <td><%=MapKit.getValueFromMap(bean.getTeacherId(), courseInfoMap)%></td>
                                        <td><a  href="TeacherEvaluateManage.do?classId=<%=bean.getClassId()%>&teacherId=<%=bean.getTeacherId()%>">评价</a></td>
                                    </tr>
                                    <%} %>
                                </table>
                                <%if(!isGovernment) {%>
                                <div class="btn-wrp">
                                    <div class="btn-wrp text-right">
                                        <a   onclick="javascript:location.href='TrainingClassTeacherManage.do?classId=<%=classInfoBean.getClassId()%>'"  class="btn btn-mini btn-success">选择授课老师</a>
                                    </div>
                                </div>
                                <%} %>
                            </div>
                        </div>
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
</script>
</html>