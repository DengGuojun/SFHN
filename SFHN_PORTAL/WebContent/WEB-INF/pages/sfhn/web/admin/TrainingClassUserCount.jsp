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
<%		
	boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
	HashMap<String, List<String>> declareReportMap = (HashMap<String, List<String>>)request.getAttribute("declareReportMap");
	HashMap<String, List<String>> declareCollectMap = (HashMap<String, List<String>>)request.getAttribute("declareCollectMap");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>认定情况统计表</span>
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
                    <section class="section-wrp">
                        <ul class="nav nav-tabs nav-justified">
                            <li><a href="TrainingClassTargetList.do">培训情况统计</a></li>                          
                            <li><a href="TrainingClassInfoCount.do">验收情况统计</a></li>
                            <li class="active"><a href="TrainingClassUserCount.do">认定情况统计</a></li>
                        </ul>
                        <table class="table table-odd table-bordered table-center">
                            <tr>
                                <th rowspan="2">地区</th>
                                <th colspan="3">所有</th>
                                <th colspan="3">生产经营型</th>
                                <th colspan="3">专业技能型</th>
                                <th colspan="3">社会服务型</th>
                                <th colspan="3">现代青年农场主</th>
                                <th colspan="3">新型农业经营主体带头人</th>
                            </tr>
                            <tr>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                                <td>认定学员</td>
                                <td>培训学员</td>
                                <td>比例</td>
                            </tr>
                             <%for (String key : declareCollectMap.keySet()) {%>
                             <tr>
								 <td><%=key %></td>
								 <td><%=declareCollectMap.get(key).get(1) %></td>
								 <td><%=declareCollectMap.get(key).get(2) %></td>
								 <td><%=declareCollectMap.get(key).get(3) %></td>
								 <td><%=declareCollectMap.get(key).get(4) %></td>
								 <td><%=declareCollectMap.get(key).get(5) %></td>
                                 <td><%=declareCollectMap.get(key).get(6) %></td>
                                 <td><%=declareCollectMap.get(key).get(7) %></td>
                                 <td><%=declareCollectMap.get(key).get(8) %></td>
                                 <td><%=declareCollectMap.get(key).get(9) %></td>
                                 <td><%=declareCollectMap.get(key).get(10) %></td>
                                 <td><%=declareCollectMap.get(key).get(11) %></td>
                                 <td><%=declareCollectMap.get(key).get(12) %></td>
                                 <td><%=declareCollectMap.get(key).get(13) %></td>
                                 <td><%=declareCollectMap.get(key).get(14) %></td>
                                 <td><%=declareCollectMap.get(key).get(15) %></td>
                                 <td><%=declareCollectMap.get(key).get(16) %></td>
                                 <td><%=declareCollectMap.get(key).get(17) %></td>
                                 <td><%=declareCollectMap.get(key).get(18) %></td>
							</tr>
							<%} %>
                             <%for (String key : declareReportMap.keySet()) {%>
                             <tr>
								<%if(declareReportMap.get(key).get(0).equals(String.valueOf(GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE))){ %>
								<td><a href="TrainingClassUserCount.do?queryProvince=<%=key %>"><%=key %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&authStatusSelection=1"><%=declareReportMap.get(key).get(1) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>"><%=declareReportMap.get(key).get(2) %></a></td>
								<td><%=declareReportMap.get(key).get(3) %></td>
								<td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(4) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>"><%=declareReportMap.get(key).get(5) %></a></td>
                                <td><%=declareReportMap.get(key).get(6) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(7) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>"><%=declareReportMap.get(key).get(8) %></a></td>
                                <td><%=declareReportMap.get(key).get(9) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(10) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>"><%=declareReportMap.get(key).get(11) %></a></td>
                                <td><%=declareReportMap.get(key).get(12) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(13) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>"><%=declareReportMap.get(key).get(14) %></a></td>
                                <td><%=declareReportMap.get(key).get(15) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(16) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryProvince=<%=key %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>"><%=declareReportMap.get(key).get(17) %></a></td>
                                <td><%=declareReportMap.get(key).get(18) %></td>
								<%}else if(declareReportMap.get(key).get(0).equals(String.valueOf(GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY))){ %>
								<td><a href="TrainingClassUserCount.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>"><%=key %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&authStatusSelection=1"><%=declareReportMap.get(key).get(1) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>"><%=declareReportMap.get(key).get(2) %></a></td>
								<td><%=declareReportMap.get(key).get(3) %></td>
								<td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(4) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>"><%=declareReportMap.get(key).get(5) %></a></td>
                                <td><%=declareReportMap.get(key).get(6) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(7) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>"><%=declareReportMap.get(key).get(8) %></a></td>
                                <td><%=declareReportMap.get(key).get(9) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(10) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>"><%=declareReportMap.get(key).get(11) %></a></td>
                                <td><%=declareReportMap.get(key).get(12) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(13) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>"><%=declareReportMap.get(key).get(14) %></a></td>
                                <td><%=declareReportMap.get(key).get(15) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(16) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryCity=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>"><%=declareReportMap.get(key).get(17) %></a></td>
                                <td><%=declareReportMap.get(key).get(18) %></td>
								<%} else if(declareReportMap.get(key).get(0).equals(String.valueOf(GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION))){ %>
								<td><a href="TrainingClassUserCount.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>"><%=key %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&authStatusSelection=1"><%=declareReportMap.get(key).get(1) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>"><%=declareReportMap.get(key).get(2) %></a></td>
								<td><%=declareReportMap.get(key).get(3) %></td>
								<td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(4) %></a></td>
								<td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_PRODUCT_FARMER %>"><%=declareReportMap.get(key).get(5) %></a></td>
                                <td><%=declareReportMap.get(key).get(6) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(7) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_TECHNICAL_FARMER %>"><%=declareReportMap.get(key).get(8) %></a></td>
                                <td><%=declareReportMap.get(key).get(9) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(10) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_SERVICE_FARMER %>"><%=declareReportMap.get(key).get(11) %></a></td>
                                <td><%=declareReportMap.get(key).get(12) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(13) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_YOUNG_FARMER %>"><%=declareReportMap.get(key).get(14) %></a></td>
                                <td><%=declareReportMap.get(key).get(15) %></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>&authStatusSelection=1"><%=declareReportMap.get(key).get(16) %></a></td>
                                <td><a href="TrainingClassUserBaseList.do?queryRegion=<%=key %>&queryProvince=<%=declareReportMap.get(key).get(19) %>&queryCity=<%=declareReportMap.get(key).get(20) %>&trainingType=<%=DeclareInfoConfig.DECLARE_TYPE_LEADER_FARMER %>"><%=declareReportMap.get(key).get(17) %></a></td>
                                <td><%=declareReportMap.get(key).get(18) %></td>
								<%} else {%>
								<td><%=key %></td>
								<td><%=declareReportMap.get(key).get(1) %></td>
								<td><%=declareReportMap.get(key).get(2) %></td>
								<td><%=declareReportMap.get(key).get(3) %></td>
								<td><%=declareReportMap.get(key).get(4) %></td>
								<td><%=declareReportMap.get(key).get(5) %></td>
                                <td><%=declareReportMap.get(key).get(6) %></td>
                                <td><%=declareReportMap.get(key).get(7) %></td>
                                <td><%=declareReportMap.get(key).get(8) %></td>
                                <td><%=declareReportMap.get(key).get(9) %></td>
                                <td><%=declareReportMap.get(key).get(10) %></td>
                                <td><%=declareReportMap.get(key).get(11) %></td>
                                <td><%=declareReportMap.get(key).get(12) %></td>
                                <td><%=declareReportMap.get(key).get(13) %></td>
                                <td><%=declareReportMap.get(key).get(14) %></td>
                                <td><%=declareReportMap.get(key).get(15) %></td>
                                <td><%=declareReportMap.get(key).get(16) %></td>
                                <td><%=declareReportMap.get(key).get(17) %></td>
                                <td><%=declareReportMap.get(key).get(18) %></td>
								<%}%>
							</tr>
							<%} %>
                        </table>
                    </section>
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