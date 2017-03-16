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
	List<TeacherInfoBean> list = (List<TeacherInfoBean>)request.getAttribute("TeacherInfoList");
	TrainingClassInfoBean classInfoBean = (TrainingClassInfoBean)request.getAttribute("TrainingClassInfoBean");
	Map<Integer,String> majorInfoMap = (Map<Integer,String>)request.getAttribute("MajorInfoMap");
	Map<Integer,String> majorTypeMap = (Map<Integer,String>)request.getAttribute("MajorTypeMap");
	
	Map<Integer,TrainingClassTeacherBean> trainingClassTeacherMap = (Map<Integer,TrainingClassTeacherBean>)request.getAttribute("TrainingClassTeacherMap");
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
                    <span class="u-title"><a href="javascript:history.go(-1);"><i class="icon-return"></i></a>选择授课老师</span>
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
                 	<form >
                 	<input type="hidden" name="classId" value="<%=classInfoBean.getClassId() %>"/>
                    <div class="search-box">
                        <div class="col-xs-12">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <div class="form-label text-right col-xs-3">查询条件:</div>
                                    <div class="col-xs-3">
                                         <input type="text" class="form-control" name="teacherName" placeholder="教师名称" value="<%=ParamKit.getParameter(request, "teacherName", "") %>" >
                                    </div>
                                    <div class="col-xs-3">
                                         <input type="text" class="form-control" name="mainCourse" placeholder="课程关键字" value="<%=ParamKit.getParameter(request, "mainCourse", "")%>">
                                    </div>
                                    <div class="col-xs-3">
                                         <button type="submit" class="btn btn-success btn-block">查询</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                     </form>
                     <section class="section-wrp ">             
                        <table class="table table-base table-odd">
                            <tr>
                                 <th>姓名</th>
                                 <th>类型</th>
                                 <th>专业</th>
                                 <th>所属单位</th>
                                 <th>主讲课程</th>
                                 <th>操作</th>
                            </tr>
                            <%for(TeacherInfoBean bean : list) {%>
                            <tr>
                                <td><%=bean.getTeacherName() %></td>
                                <td><%=majorTypeMap.get(bean.getTeacherId()) %></td>
                                <td><%=majorInfoMap.get(bean.getTeacherId()) %></td>
                                <td><%=bean.getCorporateName() %></td>
                                <td><%=bean.getMainCourse() %></td>
                                <td>
                                		<%if(trainingClassTeacherMap.containsKey(bean.getTeacherId())) {%>
                                		已添加
                                		<%}else{ %>
                                		<input type="radio" name="subBox"  value="<%=bean.getTeacherId() %>"/>
                                		<%} %>
                                </td>
                            </tr>
                            <%} %>
                        </table>            
                   </section>
                   <div class="btn-wrp">
                        <div class="btn-wrp text-right">
                            <a  id="next" class="btn btn-mini btn-default">下一步</a>
                        </div>
                    </div>
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
$("#next").click(function() {
	  var selectedTeacher="";
	  $("input[name='subBox']:checked").each(function () {
        selectedTeacher += $(this).val(); 
    	  });	
	  if(selectedTeacher==""){
		  alert("请选择授课老师");
		  return;
	  }
	  window.location = 'TrainingClassCourseSelect.do?classId='+<%=classInfoBean.getClassId()%>+'&teacherId='+selectedTeacher
	  
});
</script>
</html>