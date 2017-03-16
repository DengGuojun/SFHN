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
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%
List<String> list = (List<String>)request.getAttribute("message");
List<String> messageDeleteList = (List<String>)request.getAttribute("messageDelete");
boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
String userName = (String)request.getAttribute("UserName");
Integer unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
List<String> userInfoList = (List<String>)request.getAttribute("userInfoList");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>上传结果</span>
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
                       <%if(!list.isEmpty() && list.size() > 0){ %>
                        <h2 class="section-wrp-title"><%=list.get(0) %>
                        </h2>
                        <%} %>
                        <table class="table table-base table-odd">
                        <%for(int i = 1; i < list.size(); i++) { %>
                        <%String[] sourceStrArray = list.get(i).split(",", 3); %>
                            <tr>
                                <td><%=sourceStrArray[0]%></td>
                                <td><%=sourceStrArray[1]%></td>
                                <td><%=sourceStrArray[2]%></td>
                            </tr>
                          <%} %>
                        </table>
                    </section>
                  <%if(messageDeleteList != null &&!messageDeleteList.isEmpty()){ %>
			       <section class="section-wrp">
				    <table class="table table-base table-odd">
				<%for(int i = 0; i < messageDeleteList.size(); i++) { %>
				<%String[] sourceStrArray = messageDeleteList.get(i).split(",", 3); %>
		                     <tr>
                                <td><%=sourceStrArray[0]%></td>
                                <td><%=sourceStrArray[1]%></td>
                                <td><%=sourceStrArray[2]%></td>
                            </tr>				  
		         <%} %>
		          </table>
			       </section>
			      <%} %>
                    <%if(!userInfoList.isEmpty() && userInfoList.size() > 0){ %>
                    <section class="section-wrp">
                        <h2 class="section-wrp-title">导入信息</h2>
                        <table class="table table-base table-odd">
                            <tr>
                                <th>姓名</th>
                                <th>身份证号</th>
                                <th>学历</th>
                                <th>性别</th>
                                <th>规模</th>
                                <th>年龄</th>
                                <th>是否参加过培训</th>
                            </tr>
                             <%for(int i = 0; i < userInfoList.size(); i++) { %>
                             <%String[] sourceStrArray = userInfoList.get(i).split(",", 7); %>
                            <tr>
                                <td><%=sourceStrArray[0] %></td>
                                <td><%=sourceStrArray[1] %></td>
                                <td><%=sourceStrArray[2] %></td>
                                <td><%=sourceStrArray[3] %></td>
                                <td><%=sourceStrArray[4] %></td>
                                <td><%=sourceStrArray[5] %></td>
                                <td><%=sourceStrArray[6] %></td>
                            </tr>
                          <%} %>
                        </table>
                    </section>
                    <%} %>
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