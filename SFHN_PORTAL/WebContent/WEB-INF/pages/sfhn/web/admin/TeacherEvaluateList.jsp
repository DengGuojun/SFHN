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
	List<TeacherEvaluateBean> list = (List<TeacherEvaluateBean>)request.getAttribute("TeacherEvaluateList");
	Map<Integer,TrainingClassInfoBean> classInfoMap = (Map<Integer,TrainingClassInfoBean>)request.getAttribute("ClassInfoMap");
	Map<Integer,GovernmentOrganizationInfoBean> governmentOrgMap = (Map<Integer,GovernmentOrganizationInfoBean>)request.getAttribute("GovernmentOrgMap");
	Map<Integer,TrainingOrganizationInfoBean> trainingOrgMap = (Map<Integer,TrainingOrganizationInfoBean>)request.getAttribute("TrainingOrgMap");
	PageBean PAGE_BEAN = (PageBean)request.getAttribute("PageResult");
	List<String[]> COND_LIST = (List<String[]>)request.getAttribute("CondList");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>师资评价记录</span>
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
                 <section class="section-wrp section-scroll">             
                        <table class="table table-base table-odd">
                            <tr>
                                 <th>班级</th>
                                 <th>年份</th>
                                 <th>评价主体</th>
                                 <th>等级</th>
                                 <th>评价</th>
                                 <th>时间</th>
                            </tr>
                            <%for(TeacherEvaluateBean bean : list) {%>
                            <tr>
                                <td><%=classInfoMap.get(bean.getEvaluateId()).getClassName()%></td>
                                <td><%=classInfoMap.get(bean.getEvaluateId()).getTrainingYear()%></td>
                                <td><%=governmentOrgMap.containsKey(bean.getEvaluateId()) ? governmentOrgMap.get(bean.getEvaluateId()).getOrganizationName() : trainingOrgMap.get(bean.getEvaluateId()).getOrganizationName()%></td>
                                <td><%=governmentOrgMap.containsKey(bean.getEvaluateId()) ? GovernmentOrganizationConfig.ORGANIZATION_LEVEL_MAP.get(governmentOrgMap.get(bean.getEvaluateId()).getOrganizationLevel()) : "培训机构" %></td>
                                <td><%=bean.getEvaluateScore()%></td>
                                <td><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_TIME_FORMAT) %></td>
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
</script>
</html>