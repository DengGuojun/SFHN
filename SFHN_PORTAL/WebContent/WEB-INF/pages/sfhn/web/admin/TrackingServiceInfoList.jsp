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
	List<TrackingServiceInfoBean> list = (List<TrackingServiceInfoBean>)request.getAttribute("TrackingServiceInfoList");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
	int classId = (Integer)request.getAttribute("ClassId");
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
    String userName = (String)request.getAttribute("UserName");
    TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
    Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
    GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
    String organizationName = (String)request.getAttribute("OrganizationName");
%>
<%@ include file="../../../include/header.jsp" %>
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
            <div class="col-xs-4 left-side">
                <div class="row">
                     <div class="main-hd">
                        <div class="manage-logo"></div>
                        <%if(isGovernment){ %>
                        <p class="mg-title">主管部门: <sapn><%=userName %></sapn></p>
                        <%}else{ %>
                        <p class="mg-title">培育机构: <sapn><%=userName %></sapn></p>
                        <%} %>
                    </div>
                    <div class="main-bd" id="wrap">
                        <ul class="nav-box" id="cont">
                            <!-- 显示提示 has-tips 选中 selected -->
                            <li class="nav-item"><a href="Index.do">首页</a></li>
                            <%if(unreadMessageCount != 0){ %>
                            <li class="nav-item has-tips"><a href="MessageInfoList.do">待办通知</a><span class="num"><%=unreadMessageCount %></span></li>
                            <%}else{ %>
                            <li class="nav-item has-tips"><a href="MessageInfoList.do">待办通知</a></li>
                            <%} %>
                            <li class="nav-item"><a href="TrainingClassInfoList.do">培训班业务</a></li>
                            <%if(isGovernment){ %>
                            <li class="nav-item"><a href="TrainingClassInfoMonitorList.do">实时监控</a></li>
                             <%} %>
                            <li class="nav-item"><a href="TrainingClassUserBaseList.do">学员信息库</a></li>
                            <%if(isGovernment){ %>
                            <li class="nav-item"><a href="TeacherInfoList.do">师资信息库</a></li>
                            <%} %>
                            <li class="nav-item"><a href="TrainingClassTargetList.do">培训情况统计</a></li>
                            <%if(isGovernment && governmentOrgInfoBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE){ %>
                            <li class="nav-item"><a href="AnnouncementInfoList.do">公告通知</a></li>
                            <%} %>
                        </ul>
                    </div>
                    
                </div>
            </div>
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
                            <li><a href="TrainingClassUserList.do?classId=<%=classInfoBean.getClassId()%>">学员信息</a></li>
                            <li ><a href="TrainingClassTeacherList.do?classId=<%=classInfoBean.getClassId()%>">教师信息</a></li>
                            <li><a href="TrainingClassCentralizedManage.do?classId=<%=classInfoBean.getClassId()%>">集中培训材料</a></li>
                            <li><a href="TrainingClassFieldManage.do?classId=<%=classInfoBean.getClassId()%>">田间实训材料</a></li>
                            <li class="active"><a href="TrackingServiceInfoList.do?classId=<%=classInfoBean.getClassId()%>">跟踪服务</a></li>
                             <%if(isGovernment){ %>
                            <li><a href="TrainingClassUserEvaluate.do?classId=<%=classInfoBean.getClassId()%>">学员评定</a></li>
                            <%} %>
                        </ul>
                        <div class="nav-content">
                            <div class="nav-item">
                                <table class="table table-base table-odd">
                    		<tr class="bortop-no">
                    			<th>跟踪问题</th>
                    			<th>方案</th>
                    			<th>时间</th>
                    			<th>类型</th>
                    			<th>评价</th>
                    		</tr>
                                    <%for(TrackingServiceInfoBean bean : list) {%>
                                    <tr>
                                        <td><%=bean.getTrackingTitle() %></td>
                                        <td><%=bean.getTrackingContent() %></td>
                                        <td><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT) %></td>
                                        <td><%=bean.getServiceType() %></td>
                                        <td><%=bean.getTrackingFeedback() %></td>
                                    </tr>
                                    <%} %>
                                </table>
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