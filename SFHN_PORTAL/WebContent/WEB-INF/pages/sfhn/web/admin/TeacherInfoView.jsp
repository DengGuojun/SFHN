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
TeacherInfoBean bean = (TeacherInfoBean)request.getAttribute("TeacherInfoBean");
boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
String userName = (String)request.getAttribute("UserName");
MajorInfoBean majorInfoBean = (MajorInfoBean)request.getAttribute("MajorInfoBean");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>师资信息库</span>
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
                                <td class="frm-label">姓名</td>
                                <td class="frm-content"><%=bean.getTeacherName()%></td>
                            </tr>
                             <tr>
                                <td class="frm-label">性别</td>
                                <td class="frm-content"><%=MapKit.getValueFromMap(bean.getTeacherGender(), GenderConfig.GENDER_MAP) %></td>
                            </tr>  
                            <tr>
                                <td class="frm-label">身份证号</td>
                                <td class="frm-content"><%=bean.getIdentityNumber() %></td>
                            </tr>                         
                        </table>
                        
                         <table class="table table-base table-odd pt-19">
                            <tr>
                                <td class="frm-label">专业</td>
                                <td class="frm-content"><%=majorInfoBean.getMajorName() %></td>
                            </tr>
                            <tr>
                                <td class="frm-label">主讲课程</td>
                                <td class="frm-content"><%=bean.getMainCourse()%></td>
                            </tr>
                        </table>
                         <table class="table table-base table-odd pt-19">
                            <tr>
                                <td class="frm-label">手机</td>
                                <td class="frm-content"><%=bean.getTeacherMobile() %></td>
                            </tr>
                            <tr>
                                <td class="frm-label">地区</td>
                                <td class="frm-content"><%=bean.getProvince() %></td>
                            </tr>
                            <tr>
                                <td class="frm-label">工作单位</td>
                                <td class="frm-content"><%=bean.getCorporateName()%></td>
                            </tr>
                            <tr>
                                <td class="frm-label">家庭住址</td>
                                <td class="frm-content"><%=bean.getAddress() %></td>
                            </tr>                          
                        </table>
                    </section>
					<p class="global-tips"><a href="TeacherEvaluateList.do?teacherId=<%=bean.getTeacherId()%>">查看教师评价记录&gt;</a></p>
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