<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"  %>
<%@ page import="com.lpmas.sfhn.portal.bean.*"  %>
<%@ page import="com.lpmas.sfhn.portal.business.*"  %>
<%@ page import="com.lpmas.framework.util.*"  %>
<%@ page import="com.lpmas.sfhn.bean.*"  %>
<%@ page import="com.lpmas.sfhn.portal.config.*"  %>
<%@ page import="com.lpmas.sfhn.config.*"  %>
<%
    		boolean isGovernment = (Boolean)request.getAttribute("isGovernment");
		int count1 = (Integer)request.getAttribute("Count1");
		int count2 = (Integer)request.getAttribute("Count2");
		int count3 = (Integer)request.getAttribute("Count3");
		int count4 = (Integer)request.getAttribute("Count4");
		int count5 = (Integer)request.getAttribute("Count5");
        String userName = (String)request.getAttribute("UserName");
        List<MessageInfoBean> messageInfoList = (List<MessageInfoBean>)request.getAttribute("MessageInfoList");
        int unreadMessageCount = (Integer)request.getAttribute("unreadMessageCount");
        GovernmentOrganizationInfoBean governmentOrgInfoBean = (GovernmentOrganizationInfoBean)request.getAttribute("GovernmentOrgInfoBean");
        MessageInfoHelper helper = new MessageInfoHelper();
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
                    <span class="u-title">首页</span>
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
                <div class="wallpaper">
                    <img src="<%=STATIC_URL %>images/wallpaper.png" />
                </div>
                <div class="main-bd">
                    <section class="section-wrp">
                        <h2 class="section-wrp-title">快速进入</h2>
                        <div class="menu-wrp shadow">
                            <ul class="module-box">
                            <%if(isGovernment){ %>                                  
                            <%if(governmentOrgInfoBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_REGION){ %>
                             <li class="module-item icon-course">
                                    <a href="TrainingClassInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培训审批</span>
                                    </a>
                             </li>
                            <li class="module-item icon-apply">
                                    <a href="TrainingClassRecruitManage.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">上传招生名单</span>
                                    </a>
                             </li>
                             <li class="module-item icon-apply">
                                    <a href="TrainingClassUserImport.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">导入学员台账</span>
                                    </a>
                                </li>
                              <li class="module-item icon-teacher">
                                    <a href="TrainingClassUserSearch.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">招生筛选查询</span>
                                    </a>
                               </li>
                            <%} %>
                             <%if(governmentOrgInfoBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_CITY){ %>
                             <li class="module-item icon-course">
                                    <a href="TrainingClassInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培训审批</span>
                                    </a>
                             </li>
                            <%} %>
                            <%if(governmentOrgInfoBean.getOrganizationLevel() == GovernmentOrganizationConfig.ORGANIZATION_LEVEL_PROVINCE){ %>
                            <li class="module-item icon-course">
                                    <a href="TrainingClassInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培训审批</span>
                                    </a>
                             </li>
                            <li class="module-item icon-apply">
                                    <a href="AnnouncementInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">发公告</span>
                                    </a>
                             </li>
                            <%} %>
                             <li class="module-item icon-apply-2">
                                    <a href="TrainingClassTargetList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">数据统计</span>
                                    </a>
                              </li>
                            <li class="module-item icon-count" id="eduStatistics">
                                    <a >
                                        <i class="icon-common"></i>
                                        <span class="module-title">线上数据统计</span>
                                    </a>
                            </li>
                            <li class="module-item icon-trainLib" id="eduStatistics">
                                    <a  href="TrainingClassUserBaseList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培育对象库</span>
                                    </a>
                            </li>
                            <li class="module-item icon-teacherLib" id="eduStatistics">
                                    <a  href="TeacherInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培育师资库</span>
                                    </a>
                            </li>
                            <li class="module-item icon-lessonLib" id="eduStatistics">
                                    <a href="TrainingCourseInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培育课程库</span>
                                    </a>
                            </li>
                            <li class="module-item icon-live" id="eduStatistics">
                                    <a href="TrainingClassInfoMonitorList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">现场监控</span>
                                    </a>
                            </li>
                            <%} else{%>
                                <li class="module-item icon-course">
                                    <a href="TrainingClassInfoManage.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">培训班开班申请</span>
                                    </a>
                                </li>
                                <li class="module-item icon-apply-2">
                                    <a href="TrainingClassAcceptManage.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">项目验收申请</span>
                                    </a>
                                </li>               
                                <li class="module-item icon-apply">
                                    <a href="TrainingClassUserImport.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">导入学员台账</span>
                                    </a>
                                </li>
                                 <li class="module-item icon-teacher">
                                    <a href="TrainingClassUserSearch.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">招生筛选查询</span>
                                    </a>
                                </li>
                                 <li class="module-item icon-student">
                                    <a href="TrainingClassInfoList.do">
                                        <i class="icon-common"></i>
                                        <span class="module-title">查看本地培训班</span>
                                    </a>
                                </li> 
                              <%}%>
                            </ul>
                        </div>
                    </section>
                    <section class="section-wrp">
                        <h2 class="section-wrp-title">正在开班</h2>
                        <div class="menu-wrp shadow">
                            <ul class="module-box class-list">
                                <li class="module-item icon-classType1">
                                    <div class="view-num num"><%=count2 %></div>
                                    <a href="TrainingClassInfoList.do?trainingType=2&openStatus=<%=TrainingClassInfoConfig.OPEN_STATUS_OPENED%>">
                                        <i class="icon-common"></i>
                                        <span class="module-title">生产经营型</span>
                                    </a>
                                </li>
                                <li class="module-item icon-classType2">
                                    <div class="view-num num"><%=count3 %></div>
                                    <a href="TrainingClassInfoList.do?trainingType=3&openStatus=<%=TrainingClassInfoConfig.OPEN_STATUS_OPENED%>">
                                        <i class="icon-common"></i>
                                        <span class="module-title">专业技能型</span>
                                    </a>
                                </li>
                                <li class="module-item icon-classType3">
                                    <div class="view-num num"><%=count4 %></div>
                                    <a href="TrainingClassInfoList.do?trainingType=4&openStatus=<%=TrainingClassInfoConfig.OPEN_STATUS_OPENED%>">
                                        <i class="icon-common"></i>
                                        <span class="module-title">专业服务型</span>
                                    </a>
                                </li>
                                <li class="module-item icon-classType4">
                                    <div class="view-num num"><%=count1 %></div>
                                    <a href="TrainingClassInfoList.do?trainingType=1&openStatus=<%=TrainingClassInfoConfig.OPEN_STATUS_OPENED%>">
                                        <i class="icon-common"></i>
                                        <span class="module-title">现代青年农场主</span>
                                    </a>
                                </li>
                                <li class="module-item icon-classType5">
                                    <div class="view-num num"><%=count5 %></div>
                                    <a href="TrainingClassInfoList.do?trainingType=5&openStatus=<%=TrainingClassInfoConfig.OPEN_STATUS_OPENED%>">
                                        <i class="icon-common"></i>
                                        <span class="module-title">新型农业经营主体</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <!-- change 0924 -->
                    </section>
                     <%if(messageInfoList.size() != 0){ 
                       int count= 0;%>
                    <section class="section-wrp">
                        <h2 class="section-wrp-title">代办通知</h2>
                        <table class="table table-base table-odd">
                        <%for(MessageInfoBean bean : messageInfoList) {%>
                         <%if(count<5){ %>
                            <tr>
                                <td ><a href="<%=helper.getMessageInfoDetailUrl(bean)%>">【<%=MapKit.getValueFromMap(bean.getMessageType(), MessageInfoConfig.MESSAGE_TYPE_MAP) %>】<%=helper.getMessageTitle(bean) %></a></td>
                                <td class="text-right"><%=helper.getSendOrganizationName(bean) %></td>
                                <td class="text-right"><%=DateKit.formatTimestamp(bean.getCreateTime(), DateKit.DEFAULT_DATE_FORMAT) %></td>
                            </tr>
                           <%++count;} %>
                       <%} %>
                        </table>
                    </section>
                    <%} %>
                </div>
            </div>
        </div>
    </div>
    <script src="<%=STATIC_URL %>js/jquery.min.js"></script>
    <script src="<%=STATIC_URL %>js/layer/layer.js"></script>
    <script src="<%=STATIC_URL %>js/bootstrap.min.js"></script>
    <script src="<%=STATIC_URL %>js/mScroller.js"></script>
    <script type="text/javascript">
        mScroller();
        var layerWidth  =$(window).width() - 220 ;
        $('#eduStatistics').on('click', function(){
        	  layer.open({
        	  type: 2,
        	  title:false,
        	  maxmin: false,
        	  shadeClose: true, //点击遮罩关闭层
        	  closeBtn: 0,
        	  area : [layerWidth+'px', '100%'],
        	  offset: ['0px', '220px'],
        	  content: 'http://edu.1haowenjian.cn/pc/statistics/index'
        	  });
        	});
    </script>
  </body>
</html>